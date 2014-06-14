package platform.web.widget;

import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import platform.api.Property;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.PropertyService;

/**
 *
 * @author rodrigo
 */
public class UIAreaComponent implements WidgetComponent {

    private static final Logger logger = Logger.getLogger(UIAreaComponent.class);
    
    private String widgetId;
    
    private LineChart chart;
    private CartesianChartModel model;

    private Date updatedTime;
    
    private Widget widget;

    private PropertyService service;

    public UIAreaComponent(Widget widget, PropertyService service) {
        this.widget = widget;
        this.service = service;
    }

    @Override
    public String getTitle() {
        return widget.getTitle();
    }

    @Override
    public Object createComponent(String widgetId) {
        this.widgetId = widgetId;
        
        updatedTime = new Date();

        model = new CartesianChartModel();

        for (Property prop : widget.getProperties()) {
            ChartSeries series = new ChartSeries(prop.getDevice().getKey());
            
            // TODO buscar os valores atualizados da propriedade do device
            Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
            
            series.set(prop.getKey(), randomNum);
            model.addSeries(series);
        }

        chart = new LineChart();
        chart.setFill(true);
        chart.setStacked(true);
        chart.setValue(model);

        return chart;
    }

    @Override
    public void update() {
        if (updatedTime != null) {
            Date now = new Date();
            long intervalMilis = now.getTime() - updatedTime.getTime();
            if (intervalMilis >= (widget.getIntervals().get(0) * 1000)) {
                for (ChartSeries series : model.getSeries()) {
                    String deviceKey = series.getLabel();
                    String propertyKey = (String) 
                            series.getData().keySet().iterator().next();
                    service.get(deviceKey, propertyKey);
                    
                    // TODO
                    Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
                    
                    series.set(propertyKey, randomNum);
                }
                
                logger.debug("Updated area widget '" + widgetId + "' at " 
                        + new Date());
            }

        }
    }

    @Override
    public String getType() {
        return WidgetType.AREA;
    }    

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setService(Object service) {
        this.service = (PropertyService) service;
    }

    @Override
    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public String getWidgetId() {
        return widgetId;
    }
    
}
