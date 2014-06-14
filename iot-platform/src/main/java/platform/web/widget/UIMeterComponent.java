package platform.web.widget;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.model.Widget;
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

    private Widget widget;

    private PropertyService service;

    public UIMeterComponent(Widget widget, PropertyService service) {
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

        meterModel = new MeterGaugeChartModel(0, (List) widget.getIntervals());

        meterChart = new MeterGaugeChart();
        meterChart.setLabel(widget.getLabel());
        meterChart.setTitle(widget.getTitle());
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
