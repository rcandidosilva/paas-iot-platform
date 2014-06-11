package platform.web.widget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import platform.api.Property;
import platform.service.PropertyService;

@Named
@Dependent
public class LineWidget implements WidgetComponent {

    private LineChart chart;
    private CartesianChartModel model;
    
    private String title;
    private Property property;
    private Integer interval;
    
    private int count;
    private Date updatedTime;
    
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Inject
    private PropertyService service;

    public void init(String title, 
            String deviceKey, String propertyKey, Integer interval) {
        this.title = title;
        this.interval = interval;
        this.property = service.get(deviceKey, propertyKey);
    } 

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object create(String widgetId) {
        model = new CartesianChartModel();
        
        updatedTime = new Date();
        
        ChartSeries series1 = new ChartSeries(property.getKey());
        // TODO buscar os valores atualizados da propriedade do device
        Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
        series1.set(++count, randomNum);
        model.addSeries(series1);
        
        chart = new LineChart();
        chart.setValue(model);
        
        return chart;
    }

    @Override
    public void update() {
        if (updatedTime != null) {
            Date now = new Date();
            long intervalMilis = now.getTime() - updatedTime.getTime();
            if (intervalMilis >= (interval * 1000)) {
                // TODO buscar os valores atualizados da propriedade do device
                Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
                ChartSeries series1 = model.getSeries().get(0);
                Map<Object, Number> data = series1.getData();
                if (data.size() == 20) {
                    Object key = data.keySet().iterator().next();
                    data.remove(key);
                }
                series1.set(++count, randomNum);
                System.out.println("Updated line using " + randomNum);
            }
        }
        
    }

}
