package platform.web.widget;

import java.util.List;
import java.util.Random;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.enterprise.context.Dependent;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.poll.Poll;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.service.PropertyService;

@Named
@Dependent
public class GaugeMeterWidget implements WidgetComponent {

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
    public Panel create(String widgetId) {
        String chartId = "gaugeChart_" + System.currentTimeMillis();

        meterModel = new MeterGaugeChartModel(0, intervals);

        meterChart = new MeterGaugeChart();
        meterChart.setId(chartId);
        meterChart.setLabel(label);
        meterChart.setTitle(title);
        meterChart.setValue(meterModel);

        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

        panel = (Panel) application.createComponent(Panel.COMPONENT_TYPE);
        panel.setId(widgetId);
        panel.setHeader(title);
        panel.setClosable(true);
        panel.setToggleable(true);
        panel.setStyle("width: 400px; height: 330px;");

        Poll ajaxPoll = new Poll();
        ajaxPoll.setInterval(3);
        ajaxPoll.setUpdate(widgetId);
        String el = "#{dashboardController.updateWidget('" + widgetId + "')}";
        ExpressionFactory factory = ExpressionFactory.newInstance();
        MethodExpression expression = factory.createMethodExpression(context.getELContext(),
                el, null, new Class[]{String.class});
        ajaxPoll.setListener(expression);

        panel.getChildren().add(meterChart);
        panel.getChildren().add(ajaxPoll);
        return panel;
    }

    @Override
    public void update() {
        // TODO buscar os valores atualizados da propriedade do device
        Random rand = new Random();
        Integer randomNum = rand.nextInt((100 - 0) + 1) + 1;
        meterModel.setValue(randomNum);
        System.out.println("Updated gauge-meter using " + randomNum);
    }

}
