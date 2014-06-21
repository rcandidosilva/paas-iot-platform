package platform.web;

import platform.web.ide.WidgetFactory;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import platform.model.Application;
import platform.model.ApplicationToWidget;
import platform.model.Widget;
import platform.service.ApplicationService;
import platform.service.WidgetTypeService;
import platform.web.ide.widget.ui.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class RuntimeController implements Serializable {

    private static final Logger logger = Logger.getLogger(RuntimeController.class);

    private String applicationId;
    private Application application;

    private Dashboard dashboard;
    private DashboardModel model;

    private Map<String, WidgetComponent> widgets;

    private int widgetCount;

    @Inject
    private ApplicationService applicationService;

    @Inject
    private WidgetTypeService widgetService;

    @Inject
    private ApplicationController appController;

    @Inject
    private WidgetFactory factory;

    public void init() {
        this.model = new DefaultDashboardModel();
        this.model.addColumn(new DefaultDashboardColumn());
        this.model.addColumn(new DefaultDashboardColumn());
        this.model.addColumn(new DefaultDashboardColumn());

        this.dashboard = new Dashboard();
        this.dashboard.setDisabled(true);
        this.dashboard.setModel(model);

        this.widgets = new HashMap<>();
        if (widgets != null) {
            for (ApplicationToWidget widget : application.getWidgets()) {
                WidgetComponent component = factory.createComponent(
                        widget.getWidget(), widget.getWidgetId());
                this.widgets.put(widget.getWidgetId(), component);
            }
        }
        initWidgets();
    }

    private void initWidgets() {
        for (String widgetId : widgets.keySet()) {
            WidgetComponent component = widgets.get(widgetId);
            Object uiComponent = component.createComponent(widgetId);

            Panel panel = (Panel) JSFHelper.createComponent(Panel.COMPONENT_TYPE);
            panel.setId(widgetId);
            panel.setHeader(component.getTitle());
            panel.setClosable(false);
            panel.setToggleable(false);
            panel.setStyle("width: 400px; height: 330px;");
            panel.getChildren().add((UIComponent) uiComponent);

            this.dashboard.getChildren().add(panel);

            Widget widget = component.getWidget();
            DashboardColumn column = model.getColumn(widget.getColumnIndex());
            column.addWidget(widget.getColumnPosition(), widgetId);

            this.widgets.put(widgetId, component);

            logger.debug("Added a new widget '" + widgetId + "' to the dashboard");
        }
    }

    public void updateWidgets() {
        for (String widgetId : widgets.keySet()) {
            WidgetComponent component = widgets.get(widgetId);
            component.update();
        }
        logger.debug("Updated widgets at the application '"
                + getApplication().getName() + "' preview");
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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        if (applicationId != null && !"".equals(applicationId)) {
            this.application = applicationService.load(applicationId);
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
