package platform.web.widget;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.primefaces.component.chart.ohlc.OhlcChart;
import org.primefaces.model.chart.OhlcChartModel;

/**
 *
 * @author rodrigo
 */
@Named
@Dependent
public class OhlcWidget implements WidgetComponent {
    
    private String title;
    
    private OhlcChart chart;
    private OhlcChartModel model;
    
    public void init() {
        
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object create(String widgetId) {
        model = new OhlcChartModel();
        
        //OhlcChartSeries series1 = new OhlcChartSeries
        
        return null;
    }

    @Override
    public void update() {
        // TODO
    }
    
    
    
}
