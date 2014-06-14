package platform.web;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.poll.Poll;
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
import platform.service.WidgetService;
import platform.service.WidgetTypeService;
import platform.web.widget.WidgetComponent;

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

    @PostConstruct
    public void init() {
        model = new DefaultDashboardModel();
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
        widgets = new HashMap<>();
    }

    public void dropWidget(DragDropEvent event) {
        Object data = event.getData();
        if (data instanceof WidgetType) {
            WidgetType widget = (WidgetType) data;
            String name = widget.getName();
            if (WidgetType.METER.equals(name)) {
                // TODO create meter-gauge
            } else if (WidgetType.BAR.equals(name)) {
                // TODO create bar-char
            } else if (WidgetType.LOCATION.equals(name)) {
                // TODO create geo-location
            } else if (WidgetType.PIE.equals(name)) {
                // TODO create pie-chart
            } else if (WidgetType.OHLC.equals(name)) {
                // TODO create ohlc-chart
            }

        }
    }

    public void addWidget(WidgetComponent widget) {

        String widgetId = "widget" + ++widgetCount;

        logger.debug("Adding a new widget '" + widgetId + "' to the dashboard");

        FacesContext context = FacesContext.getCurrentInstance();

        Object component = widget.create(widgetId);

        Panel panel = (Panel) context.getApplication().createComponent(
                Panel.COMPONENT_TYPE);
        panel.setId(widgetId);
        panel.setHeader(widget.getTitle());
        panel.setClosable(true);
        panel.setToggleable(true);
        panel.setStyle("width: 400px; height: 330px;");

        Poll ajaxPoll = new Poll();
        ajaxPoll.setInterval(3);
        ajaxPoll.setGlobal(false);
        ajaxPoll.setUpdate(widgetId);
        String el = "#{dashboardController.updateWidget('" + widgetId + "')}";
        ExpressionFactory factory = ExpressionFactory.newInstance();
        MethodExpression expression = factory.createMethodExpression(context.getELContext(),
                el, null, new Class[]{String.class});
        ajaxPoll.setListener(expression);

        panel.getChildren().add((UIComponent) component);
        panel.getChildren().add(ajaxPoll);

        getDashboard().getChildren().add(panel);

        DashboardColumn column = model.getColumn(0);
        column.addWidget(panel.getId());

        widgets.put(widgetId, widget);
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

    public void updateWidget(String widgetId) {
        if (widgets != null) {
            WidgetComponent widget = widgets.get(widgetId);
            if (widget != null) {
                widget.update();
            }
        }
    }

    public Application getApplication() {
        return appController.getApplication();
    }

    public void setApplication(Application application) {
        appController.setApplication(application);
    }

    public String edit(Application application) {

        for (ApplicationToWidget widget : application.getWidgets()) {
            widget.getWidgetId();
            //Widget  widget.getWidget();
        }

        return "/pages/ide/index";
    }

    public void save() {
        Application application = getApplication();

        List<ApplicationToWidget> appWidgets = new ArrayList<>();
        for (String widgetId : widgets.keySet()) {

            WidgetComponent component = widgets.get(widgetId);

            WidgetType widget = widgetService.loadByType(component.getType());

            ApplicationToWidget appWidget = new ApplicationToWidget();
            appWidget.setWidgetId(widgetId);
            //appWidget.setWidget(widget);
            appWidget.setApplication(application);

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
