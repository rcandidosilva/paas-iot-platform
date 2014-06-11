package platform.web;

import java.io.ByteArrayInputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.WidgetService;
import platform.web.widget.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Named 
@SessionScoped
public class DashboardController implements Serializable {

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
        Object data = event.getData();
        if (data instanceof Widget) {
            Widget widget = (Widget) data;
            String name = widget.getName();
            if (WidgetType.METER_GAUGE.equals(name)) {
                // TODO create meter-gauge
            } else if (WidgetType.BAR_CHART.equals(name)) {
                // TODO create bar-char
            } else if (WidgetType.GEO_LOCATION.equals(name)) {
                // TODO create geo-location
            } else if (WidgetType.PIE_CHART.equals(name)) {
                // TODO create pie-chart
            } else if (WidgetType.OHLC_CHART.equals(name)) {
                // TODO create ohlc-chart
            }
            
        }
    }

    public void addWidget(WidgetComponent widget) {
        Panel panel = widget.create("widget" + ++widgetCount);
        
        getDashboard().getChildren().add(panel);
        
        DashboardColumn column = model.getColumn(0);
        column.addWidget(panel.getId());
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
    
    public StreamedContent toIconFile(Widget widget) {
        byte[] bytes = widget.getIconFile();
        return new DefaultStreamedContent(
                new ByteArrayInputStream(bytes), widget.getIconContentType());
    } 

}
