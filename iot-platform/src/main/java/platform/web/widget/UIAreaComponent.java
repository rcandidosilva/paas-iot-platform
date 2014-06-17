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
import platform.service.api.PropertyService;

/**
 *
 * @author rodrigo
 */
public class UIAreaComponent implements WidgetComponent {

    private static final Logger logger = Logger.getLogger(UIAreaComponent.class);

    private String widgetId;

    private LineChart chart;
    private CartesianChartModel model;

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

        model = new CartesianChartModel();

        for (Property prop : widget.getProperties()) {
            ChartSeries series = new ChartSeries(prop.getDevice().getKey());

            Double value = new Double(prop.getValue());
            series.set(prop.getKey(), value);
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
        for (ChartSeries series : model.getSeries()) {
            String deviceKey = series.getLabel();
            String propertyKey = (String) series.getData().keySet().iterator().next();
            Property property = service.get(deviceKey, propertyKey);

            Double value = new Double(property.getValue());
            series.set(propertyKey, value);
        }
        logger.debug("Updated area widget '" + widgetId + "' at "
                + new Date());
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
