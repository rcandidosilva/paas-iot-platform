package platform.web;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
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
        panel.setToggleable(true);
        panel.setStyle("width: 400px; height: 330px;");

        panel.getChildren().add((UIComponent) component);

        if (dashboard == null) {
            dashboard = new Dashboard();
            dashboard.setModel(model);
        } 
        dashboard.getChildren().add(panel);

        DashboardColumn column = model.getColumn(0);
        column.addWidget(panel.getId());

        widgets.put(widgetId, widget);
        
        logger.debug("Added a new widget '" + widgetId + "' to the dashboard");
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

            ApplicationToWidget appWidget = 
                    new ApplicationToWidget(widgetId, application);
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
