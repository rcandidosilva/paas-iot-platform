package platform.web.widget;

import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;
import org.primefaces.component.chart.bar.BarChart;
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
public class UIBarComponent implements WidgetComponent {

    private static final Logger logger = Logger.getLogger(UIBarComponent.class);

    private String widgetId;

    private BarChart chart; 
    private CartesianChartModel model;

    private String title;

    private Widget widget;

    private PropertyService service;

    public UIBarComponent(Widget widget, PropertyService service) {
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

            // TODO buscar os valores atualizados da propriedade do device
            Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;

            series.set(prop.getKey(), randomNum);
            model.addSeries(series);
        }

        chart = new BarChart();

        //chart.setOrientation("horizontal");
        //chart.setLegendPosition("ne");
        //chart.setYaxisAngle(90);
        chart.setValue(model);
        //chart.setStyle("height: 100px; width: 100px;");

        return chart;
    }

    @Override
    public void update() {
        for (ChartSeries series : model.getSeries()) {
            String deviceKey = series.getLabel();
            String propertyKey = (String) series.getData().keySet().iterator().next();
            service.get(deviceKey, propertyKey);

            // TODO
            Integer randomNum = new Random().nextInt((100 - 0) + 1) + 1;

            series.set(propertyKey, randomNum);
        }

        logger.debug("Updated bar widget '" + widgetId + "' at "
                + new Date());
    }

    @Override
    public String getType() {
        return WidgetType.BAR;
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
