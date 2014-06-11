package platform.web.widget;

import java.util.List;
import java.util.Random;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.service.PropertyService;

@Named
@Dependent
public class MeterWidget implements WidgetComponent {

    private Panel panel;
    private MeterGaugeChartModel meterModel;
    private MeterGaugeChart meterChart;

    private List intervals;
    private String title;
    private String label;
    private String deviceKey;
    private String propertyKey;

    @Inject
    private PropertyService service;

    public void setIntervals(List intervals) {
        this.intervals = intervals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    @Override
    public Object create(String widgetId) {
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
        System.out.println("Updated meter using " + randomNum);
    }

}
