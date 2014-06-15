package platform.web;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import platform.model.Application;
import platform.model.ApplicationToWidget;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.WidgetTypeService;
import platform.web.widget.WidgetComponent;
import platform.web.widget.WidgetComponentFactory;

/**
 *
 * @author rodrigo
 */
@Named("ideController")
@SessionScoped
public class IDEController implements Serializable {

    private static final Logger logger = Logger.getLogger(IDEController.class);

    private Dashboard dashboard;
    private DashboardModel model;

    private Map<String, WidgetComponent> widgets;

    private int widgetCount;

    @Inject
    private WidgetTypeService widgetService;

    @Inject
    private ApplicationController appController;

    @Inject
    private WidgetComponentFactory factory;

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
    }

    public void dropWidget(DragDropEvent event) {
       Object data = event.getData();
       // TODO
    }

    public void addWidget(WidgetComponent widget) {

        String widgetId = widget.getWidgetId();
        if (widgetId == null) {
            widgetId = "widget" + ++widgetCount;
            logger.debug("Generated a new widgetId: " + widgetId);
        }

        Object component = widget.createComponent(widgetId);

        Panel panel = (Panel) JSFHelper.createComponent(Panel.COMPONENT_TYPE);
        panel.setId(widgetId);
        panel.setHeader(widget.getTitle());
        panel.setClosable(true);
        panel.setToggleable(false);

        HtmlPanelGroup group = (HtmlPanelGroup) JSFHelper.createComponent(HtmlPanelGroup.COMPONENT_TYPE);

        CommandLink rightLink = (CommandLink) JSFHelper.createComponent(CommandLink.COMPONENT_TYPE);
        rightLink.setStyleClass("ui-panel-titlebar-icon ui-corner-all ui-state-default");
        HtmlOutputText rightText = (HtmlOutputText) JSFHelper.createComponent(HtmlOutputText.COMPONENT_TYPE);
        rightText.setStyleClass("ui-icon ui-icon-arrowthick-1-e");
        rightLink.getChildren().add(rightText);
        String rightEL = "#{ideController.moveWidgetToRight('" + widgetId + "')}";
        MethodExpression rightExp
                = JSFHelper.createMethodExpression(rightEL, null, new Class[]{String.class});
        rightLink.setActionExpression(rightExp);
        rightLink.setImmediate(true);
        rightLink.setUpdate(":dashboardForm");
        group.getChildren().add(rightLink);
        
        CommandLink leftLink = (CommandLink) JSFHelper.createComponent(CommandLink.COMPONENT_TYPE);
        leftLink.setStyleClass("ui-panel-titlebar-icon ui-corner-all ui-state-default");
        HtmlOutputText leftText = (HtmlOutputText) JSFHelper.createComponent(HtmlOutputText.COMPONENT_TYPE);
        leftText.setStyleClass("ui-icon ui-icon-arrowthick-1-w");
        leftLink.getChildren().add(leftText);
        String leftEL = "#{ideController.moveWidgetToLeft('" + widgetId + "')}";
        MethodExpression leftExp
                = JSFHelper.createMethodExpression(leftEL, null, new Class[]{String.class});
        leftLink.setActionExpression(leftExp);
        leftLink.setImmediate(true);
        leftLink.setUpdate(":dashboardForm");
        group.getChildren().add(leftLink);        

        panel.getFacets().put("actions", group);

        panel.setStyle("width: 400px; height: 330px;");
        panel.getChildren().add((UIComponent) component);
        
        AjaxBehavior closeAjax = (AjaxBehavior) 
                JSFHelper.createBehavior(AjaxBehavior.BEHAVIOR_ID);
        String closeEL = "#{ideController.deleteWidget('" + widgetId + "')}";
        MethodExpression closeExp
                = JSFHelper.createMethodExpression(closeEL, null, new Class[]{String.class});        
        closeAjax.setListener(closeExp);
        closeAjax.setImmediate(true);
        
        panel.addClientBehavior("close", closeAjax);

        dashboard.getChildren().add(panel);

        DashboardColumn column = model.getColumn(1);
        column.addWidget(panel.getId());

        widgets.put(widgetId, widget);

        logger.debug("Added a new widget '" + widgetId + "' to the dashboard");
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
    
    public void deleteWidget(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        column.removeWidget(widgetId);
        widgets.remove(widgetId);
        logger.debug("Widget '" + widgetId + "' deleted from the dashboard");
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

    public StreamedContent toIconFile(WidgetType widget) {
        byte[] bytes = widget.getIconFile();
        return new DefaultStreamedContent(
                new ByteArrayInputStream(bytes), widget.getIconContentType());
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
            appWidget.setWidget(widget);

            appWidgets.add(appWidget);
        }

        application.setWidgets(appWidgets);

        appController.save(application);

        JSFHelper.addSuccessMessage("Application saved successfully");

    }

    public void view() {
        // TODO
    }

    public void publish() {
        // TODO
    }

    public void delete() {
        // TODO
    }
}
