package platform.web.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;
import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import platform.api.Property;
import platform.service.PropertyService;

/**
 *
 * @author rodrigo
 */
public class BarWidget implements WidgetComponent {

    private BarChart chart;
    private CartesianChartModel model;

    private String title;
    private Integer interval;

    private List<Property> properties;

    private Date updatedTime;

    @Inject
    private PropertyService service;

    public void init(String title, Integer interval, Map<String, String> properties) {
        this.title = title;
        this.interval = interval;
        this.properties = new ArrayList<>();
        for (String deviceKey : properties.keySet()) {
            String propertyKey = properties.get(deviceKey);
            Property property = service.get(deviceKey, propertyKey);
            this.properties.add(property);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object create(String widgetId) {

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
            }

        }
    }
    
}
