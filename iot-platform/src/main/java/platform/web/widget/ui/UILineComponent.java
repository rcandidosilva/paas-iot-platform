package platform.web.widget.ui;

import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import platform.api.Property;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.api.PropertyService;

/**
 *
 * @author rodrigo
 */
public class UILineComponent implements WidgetComponent {

    private static final Logger logger = Logger.getLogger(UILineComponent.class);

    private String widgetId;

    private LineChart chart;
    private CartesianChartModel model;

    private int count;

    private Widget widget;

    private PropertyService service;

    public UILineComponent(Widget widget, PropertyService service) {
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

        model = new CartesianChartModel();

        ChartSeries series1 = new ChartSeries(widget.getProperties().get(0).getKey());
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
        if (widget.getProperties() != null
                && !widget.getProperties().isEmpty()) {
            Property property = widget.getProperties().get(0);
            property = service.get(property.getDevice().getKey(),
                    property.getKey());

            ChartSeries series1 = model.getSeries().get(0);
            Map<Object, Number> data = series1.getData();
            if (data.size() == 20) {
                Object key = data.keySet().iterator().next();
                data.remove(key);
            }

            Double value = 0d;
            try {
                value = new Double(property.getValue());
            } catch (Exception ex) {
                logger.error("Error to get the updated value for the property '"
                        + property.getKey() + "'", ex);
            }
            series1.set(++count, value);
            logger.debug("Updated line at widget '" + widgetId
                    + "' using value: " + value);
        }
    }

    @Override
    public String getType() {
        return WidgetType.LINE;
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
