package platform.web.ide;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.menu.Menu;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import platform.model.Application;
import platform.model.ApplicationToWidget;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.WidgetTypeService;
import platform.web.ApplicationController;
import platform.web.JSFHelper;
import platform.web.ide.widget.WidgetController;
import platform.web.ide.widget.ui.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Controller("ideController")
@Scope("session")
public class IDEController implements Serializable {

    private static final Logger logger = Logger.getLogger(IDEController.class);

    private Dashboard dashboard;
    private DashboardModel model;

    private Map<String, WidgetComponent> widgets;

    private int widgetCount = 1;

    @Inject
    private WidgetTypeService widgetService;

    @Inject
    private ApplicationController appController;

    @Inject
    private PreviewController previewController;

    @Inject
    private WidgetFactory factory;

    @PostConstruct
    public void init() {
        dashboard = new Dashboard();
        dashboard.setDisabled(true);

        model = new DefaultDashboardModel();
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());

        dashboard.setModel(model);

        widgets = new HashMap<>();
        // Force reset
        previewController.init(null);
    }

    private enum LinkType {

        LEFT, UP, DOWN, RIGHT
    };

    public void addWidget(WidgetComponent component) {

        String widgetId = component.getWidgetId();
        if (widgetId == null) {
            widgetId = "widget" + widgetCount;
            logger.debug("Generated a new widgetId: " + widgetId);
        }

        Object uiComponent = component.createComponent(widgetId);

        Panel panel = (Panel) JSFHelper.createComponent(Panel.COMPONENT_TYPE);
        panel.setId(widgetId);
        panel.setHeader(component.getTitle());
        panel.setClosable(false);
        panel.setToggleable(false);

        HtmlPanelGroup group = (HtmlPanelGroup) JSFHelper.createComponent(HtmlPanelGroup.COMPONENT_TYPE);

        group.getChildren().add(createLink(widgetId, LinkType.RIGHT));
        group.getChildren().add(createLink(widgetId, LinkType.UP));
        group.getChildren().add(createLink(widgetId, LinkType.DOWN));
        group.getChildren().add(createLink(widgetId, LinkType.LEFT));
        panel.getFacets().put("actions", group);

        Menu menu = createMenu(widgetId, component.getType());
        panel.getFacets().put("options", menu);

        panel.setStyle("width: 400px; height: 350px;");
        panel.getChildren().add((UIComponent) uiComponent);

        dashboard.getChildren().add(panel);

        Widget widget = component.getWidget();
        int columnIndex = widget.getColumnIndex() != null
                ? widget.getColumnIndex() : 0;
        int columnPosition = widget.getColumnPosition() != null
                ? widget.getColumnPosition() : 0;
        DashboardColumn column = model.getColumn(columnIndex);
        column.addWidget(columnPosition, panel.getId());

        widgets.put(widgetId, component);
        widgetCount++;

        logger.debug("Added a new widget '" + widgetId + "' to the dashboard");
    }

    private Menu createMenu(String widgetId, String widgetType) {
        Menu menu = (Menu) JSFHelper.createComponent(Menu.COMPONENT_TYPE);
        DefaultMenuModel menuModel = new DefaultMenuModel();
        DefaultMenuItem editItem = new DefaultMenuItem("Edit");
        editItem.setCommand("#{ideController.editWidget('" + widgetId + "')}");
        editItem.setAjax(true);
        editItem.setImmediate(true);
        editItem.setOncomplete("PF('" + widgetType + "Dialog').show();");
        editItem.setUpdate(":" + widgetType + "Form:" + widgetType + "Dialog");
        DefaultMenuItem deleteItem = new DefaultMenuItem("Delete");
        deleteItem.setCommand("#{ideController.deleteWidget('" + widgetId + "')}");
        deleteItem.setAjax(true);
        deleteItem.setImmediate(true);
        menuModel.addElement(editItem);
        menuModel.addElement(deleteItem);
        menu.setModel(menuModel);
        return menu;
    }

    private CommandLink createLink(String widgetId, LinkType type) {
        CommandLink link = (CommandLink) JSFHelper.createComponent(CommandLink.COMPONENT_TYPE);
        link.setStyleClass("ui-panel-titlebar-icon ui-corner-all ui-state-default");
        HtmlOutputText text = (HtmlOutputText) JSFHelper.createComponent(HtmlOutputText.COMPONENT_TYPE);
        String el = null;
        if (type == LinkType.LEFT) {
            text.setStyleClass("ui-icon ui-icon-arrowthick-1-w");
            el = "#{ideController.moveWidgetToLeft('" + widgetId + "')}";
        } else if (type == LinkType.RIGHT) {
            text.setStyleClass("ui-icon ui-icon-arrowthick-1-e");
            el = "#{ideController.moveWidgetToRight('" + widgetId + "')}";
        } else if (type == LinkType.UP) {
            text.setStyleClass("ui-icon ui-icon-arrowthick-1-n");
            el = "#{ideController.moveWidgetToUp('" + widgetId + "')}";
        } else if (type == LinkType.DOWN) {
            text.setStyleClass("ui-icon ui-icon-arrowthick-1-s");
            el = "#{ideController.moveWidgetToDown('" + widgetId + "')}";
        }
        MethodExpression expression
                = JSFHelper.createMethodExpression(el, null, new Class[]{String.class});
        link.getChildren().add(text);
        link.setActionExpression(expression);
        link.setImmediate(true);
        link.setUpdate(":dashboardForm");
        return link;
    }

    private DashboardColumn getDashboardColumn(String widgetId) {
        for (DashboardColumn column : model.getColumns()) {
            for (String id : column.getWidgets()) {
                if (id.equals(widgetId)) {
                    return column;
                }
            }
        }
        return null;
    }

    private int getDashboardColumnIndex(String widgetId) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            for (String id : model.getColumn(i).getWidgets()) {
                if (id.equals(widgetId)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getDashboardColumnPosition(int columnIndex, String widgetId) {
        DashboardColumn column = model.getColumn(columnIndex);
        for (int i = 0; i < column.getWidgetCount(); i++) {
            if (column.getWidget(i).equals(widgetId)) {
                return i;
            }
        }
        return -1;
    }

    public void editWidget(String widgetId) {
        WidgetComponent component = widgets.get(widgetId);
        Widget widget = component.getWidget();
        WidgetController controller = factory.getController(widget);
        controller.setWidget(widget);
        logger.debug("Widget '" + widgetId + "' requested to edit at the dashboard");
    }

    public void deleteWidget(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        column.removeWidget(widgetId);
        widgets.remove(widgetId);
        logger.debug("Widget '" + widgetId + "' deleted from the dashboard");
    }

    public void moveWidgetToUp(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        int index = 0;
        for (int i = 0; i < column.getWidgetCount(); i++) {
            if (column.getWidget(i).equals(widgetId)) {
                index = i;
                break;
            }
        }
        if (index != 0) {
            column.reorderWidget(--index, widgetId);
            logger.debug("Widget '" + widgetId + "' moved to up at column in the dashboard");
        }
    }

    public void moveWidgetToDown(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        int index = 0;
        for (int i = 0; i < column.getWidgetCount(); i++) {
            if (column.getWidget(i).equals(widgetId)) {
                index = i;
                break;
            }
        }
        if ((index + 1) != column.getWidgetCount()) {
            column.reorderWidget(++index, widgetId);
            logger.debug("Widget '" + widgetId + "' moved to down at column in the dashboard");
        }
    }

    public void moveWidgetToLeft(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        int index = model.getColumns().indexOf(column);
        if (index != 0) {
            DashboardColumn target = model.getColumn(--index);
            model.transferWidget(column, target, widgetId, 0);
            logger.debug("Widget '" + widgetId + "' moved to left columns at dashboard");
        }
    }

    public void moveWidgetToRight(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        int index = model.getColumns().indexOf(column);
        if (index < 2) {
            DashboardColumn target = model.getColumn(++index);
            model.transferWidget(column, target, widgetId, 0);
            logger.debug("Widget '" + widgetId + "' moved to right columns at dashboard");
        }
    }

    public List<WidgetType> getWidgetList() {
        return widgetService.list();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public Application getApplication() {
        return appController.getApplication();
    }

    public void setApplication(Application application) {
        appController.setApplication(application);
    }

    public String edit(Application application) {

        for (ApplicationToWidget widget : application.getWidgets()) {
            WidgetComponent component = factory.createComponent(
                    widget.getWidget(), widget.getWidgetId());
            addWidget(component);
        }

        return "/pages/ide/index";
    }

    public void save() {

        Application application = getApplication();

        List<ApplicationToWidget> appWidgets = application.getWidgets();
        if (appWidgets == null) {
            appWidgets = new ArrayList<>();
        }

        for (String widgetId : widgets.keySet()) {
            WidgetComponent component = widgets.get(widgetId);
            Widget widget = component.getWidget();

            ApplicationToWidget appWidget
                    = new ApplicationToWidget(widgetId, application);
            if (appWidgets.contains(appWidget)) {
                appWidget = appWidgets.remove(appWidgets.indexOf(appWidget));
            }

            int columnIndex = getDashboardColumnIndex(widgetId);
            int columnPosition = getDashboardColumnPosition(columnIndex,
                    widgetId);

            widget.setColumnIndex(columnIndex);
            widget.setColumnPosition(columnPosition);
            appWidget.setWidget(widget);

            appWidgets.add(appWidget);
        }

        application.setWidgets(appWidgets);

        appController.save(application);

        JSFHelper.addSuccessMessage("Application saved successfully");

    }

    public String preview() {
        this.previewController.init(widgets);
        logger.debug("Application '" + getApplication().getId() + "' requested to preview");
        return "/pages/ide/preview";
    }

    public void publish() {
        Application application = getApplication();
        application.setDeployed(true);
        application.setDeployedAt(new Date());
        String applicationUrl = JSFHelper.getBaseUrl()
                + "/faces/pages/application/runtime.xhtml?uid="
                + application.getId();
        application.setUrl(applicationUrl);
        appController.update(application);
        logger.debug("Application '" + getApplication().getId() + "' requested to publish");
        try {
            JSFHelper.sendRedirect(applicationUrl);
        } catch (IOException ex) {
            logger.error("Error to send redirect the application to the url:" + 
                    applicationUrl, ex);
        }
        FacesContext.getCurrentInstance().renderResponse();
     
    }

    public String delete() {
        appController.delete(getApplication());
        logger.debug("Application '" + getApplication().getId() + "' deleted");
        return "/pages/application/list";
    }
}
