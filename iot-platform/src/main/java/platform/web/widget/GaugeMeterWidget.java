package platform.web.widget;

import java.util.Date;
import java.util.List;
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
    
    public GaugeMeterWidget() {
        super();
    }
    
    public GaugeMeterWidget(List intervals, 
            String label, String title, String deviceKey, String propertyKey) {
        this.intervals = intervals;
        this.deviceKey = deviceKey;
        this.propertyKey = propertyKey;
        this.title = title;
        this.label = label;
    }
    
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
        panel.setStyle("width: 400; height: 300;");
        
        Poll ajaxPoll = new Poll();
        ajaxPoll.setInterval(3);
        ajaxPoll.setUpdate(chartId);
        MethodExpression expression =  
                ExpressionFactory.newInstance().createMethodExpression(
                context.getELContext(), 
                "#{gaugeMeterWidget.updateGaugeChart}", 
                null, new Class[0]);
        ajaxPoll.setListener(expression);
        
        
        panel.getChildren().add(meterChart);
        panel.getChildren().add(ajaxPoll);
        return panel;
    }
    
    public void updateGaugeChart() {
        System.out.println("updateGaugeChart executed at " + new Date());
    }
    

    
}
