package platform.web.widget.ui;

import org.primefaces.component.chart.ohlc.OhlcChart;
import org.primefaces.model.chart.OhlcChartModel;
import platform.model.Widget;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
public class UIOhlcComponent implements WidgetComponent {

    private String title;
    private String widgetId;

    private OhlcChart chart;
    private OhlcChartModel model;

    private Widget widget;

    public UIOhlcComponent(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object createComponent(String widgetId) {
        this.widgetId = widgetId;

        model = new OhlcChartModel();

        //OhlcChartSeries series1 = new OhlcChartSeries
        return null;
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public String getType() {
        return WidgetType.OHLC;
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setService(Object service) {
        // TODO
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
