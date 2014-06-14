package platform.web.widget;

import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import platform.api.Property;
import platform.model.WidgetType;
import platform.service.PropertyService;

/**
 *
 * @author rodrigo
 */
public class UIBarComponent implements WidgetComponent {

    private static final Logger logger = Logger.getLogger(UIBarComponent.class);
    
    private String widgetId;
    
    private BarChart chart;
    private CartesianChartModel model;

    private String title;
    private Integer interval;

    private List<Property> properties;

    private Date updatedTime;

    private PropertyService service;

    public UIBarComponent(String title, Integer interval, 
            List<Property> properties, PropertyService service) {
        this.title = title;
        this.interval = interval;
        this.properties = properties;
        this.service = service;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object create(String widgetId) {
        this.widgetId = widgetId;
        
        updatedTime = new Date();

        model = new CartesianChartModel();

        for (Property prop : properties) {
            ChartSeries series = new ChartSeries(prop.getDevice().getKey());
            
            // TODO buscar os valores atualizados da propriedade do device
            Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
            
            series.set(prop.getKey(), randomNum);
            model.addSeries(series);
        }

        chart = new BarChart();
        
        chart.setOrientation("horizontal");
        chart.setLegendPosition("ne");
        chart.setYaxisAngle(90);
        chart.setValue(model);

        return chart;
    }

    @Override
    public void update() {
        if (updatedTime != null) {
            Date now = new Date();
            long intervalMilis = now.getTime() - updatedTime.getTime();
            if (intervalMilis >= (interval * 1000)) {
                for (ChartSeries series : model.getSeries()) {
                    String deviceKey = series.getLabel();
                    String propertyKey = (String) 
                            series.getData().keySet().iterator().next();
                    service.get(deviceKey, propertyKey);
                    
                    // TODO
                    Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
                    
                    series.set(propertyKey, randomNum);
                }
                
                logger.debug("Updated bar widget '" + widgetId + "' at " 
                        + new Date());
            }

        }
    }

    @Override
    public String getType() {
        return WidgetType.BAR;
    }
    
}
