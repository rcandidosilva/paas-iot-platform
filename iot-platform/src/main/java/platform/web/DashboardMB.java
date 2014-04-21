package platform.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import platform.model.Widget;
import platform.service.WidgetService;

/**
 *
 * @author rodrigo
 */
@Named(value = "dashboardMB")
@SessionScoped
public class DashboardMB implements Serializable {

    @Inject
    private WidgetService widgetService;
    
    private Dashboard dashboard;
    private DashboardModel model;
    
    private int widgetCount;
    
    @PostConstruct
    public void init() {
        model = new DefaultDashboardModel();
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
        model.addColumn(new DefaultDashboardColumn());
    }
    
    public void dropWidget(DragDropEvent event) {
        createWidget();
    }

    private void createWidget() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application application = fc.getApplication();
        Panel panel = (Panel) application.createComponent(fc, 
                "org.primefaces.component.Panel", 
                "org.primefaces.component.PanelRenderer");
        panel.setId("widget" + ++widgetCount);
        panel.setHeader("Dashboard Component for " + widgetCount);
        panel.setClosable(true);
        panel.setToggleable(true);

        getDashboard().getChildren().add(panel);
        
        DashboardColumn column = model.getColumn(0);
        column.addWidget(panel.getId());
        HtmlOutputText text = new HtmlOutputText();
        text.setValue("Dashboard widget bits!");

        panel.getChildren().add(text);
    }
    
    public List<Widget> getWidgetList() {
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

}
