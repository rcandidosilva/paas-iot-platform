package platform.web.widget;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.model.WidgetType;
import platform.service.PropertyService;

/**
 * 
 * @author rodrigo
 */
public class UIMeterComponent implements WidgetComponent {
    
    private static final Logger logger = Logger.getLogger(UIMeterComponent.class);

    private String widgetId;
    
    private Panel panel;
    private MeterGaugeChartModel meterModel;
    private MeterGaugeChart meterChart;

    private List intervals;
    private String title;
    private String label;
    private String deviceKey;
    private String propertyKey;
    
    private PropertyService service;
    
    public UIMeterComponent(String title, String label, List intervals,
            String deviceKey, String propertyKey, PropertyService service) {
        this.title = title;
        this.label = label;
        this.intervals = intervals;
        this.deviceKey = deviceKey;
        this.propertyKey = propertyKey;
        this.service = service;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object create(String widgetId) {
        this.widgetId = widgetId;
        
        meterModel = new MeterGaugeChartModel(0, intervals);

        meterChart = new MeterGaugeChart();
        meterChart.setLabel(label);
        meterChart.setTitle(title);
        meterChart.setValue(meterModel);
        
        return meterChart;
    }

    @Override
    public void update() {
        // TODO buscar os valores atualizados da propriedade do device
        Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;
        meterModel.setValue(randomNum);
        
        logger.debug("Updated meter at widget '" + widgetId
                + "' using value: " + randomNum);
    }

    @Override
    public String getType() {
        return WidgetType.METER;
    }
    
}
