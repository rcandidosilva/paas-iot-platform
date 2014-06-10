/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package platform.web.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.model.chart.MeterGaugeChartModel;
import platform.web.DashboardMB;

@Named
@SessionScoped
public class GaugeMeterMB implements Serializable {
    
    private String title;
    private String label;
    private Double interval;
    private ListDataModel<Double> intervalsModel;
    
    @Inject
    private DashboardMB dashboard;
    
    @PostConstruct
    public void init() {
        intervalsModel = new ListDataModel<>(new ArrayList<Double>());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getInterval() {
        return interval;
    }

    public void setInterval(Double interval) {
        this.interval = interval;
    }

    public ListDataModel<Double> getIntervalsModel() {
        return intervalsModel;
    }
    
    public void newInterval() {
        ((List<Double>) intervalsModel.getWrappedData()).add(interval);
    }
    
    public void deleteInterval() {
        ((List<Double>) intervalsModel.getWrappedData()).remove(
                intervalsModel.getRowData());        
    }
    
    public void addAction() {
        List intervals = (List) getIntervalsModel().getWrappedData();
        
        MeterGaugeChartModel meterModel = new MeterGaugeChartModel(0, intervals);
        MeterGaugeChart meterChart = new MeterGaugeChart();
        meterChart.setLabel(label);
        meterChart.setTitle(title);
        meterChart.setValue(meterModel);
        
        dashboard.createWidget(meterChart);
        
    }
      
    
}
