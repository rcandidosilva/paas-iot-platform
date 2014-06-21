package platform.web.ide.widget.ui;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.api.Property;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.api.PropertyService;

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
        if (widget.getProperties() != null
                && !widget.getProperties().isEmpty()) {

            Property property = widget.getProperties().get(0);
            property = service.get(property.getDevice().getKey(),
                    property.getKey());

            Double value = 0d;
            try {
                value = new Double(property.getValue());
            } catch (Exception ex) {
                logger.error("Error to get the updated value for the property '"
                        + property.getKey() + "'", ex);
            }
            meterModel.setValue(value);

            logger.debug("Updated meter at widget '" + widgetId
                    + "' using value: " + value);
        }
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
