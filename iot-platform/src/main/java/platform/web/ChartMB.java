/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platform.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.chart.MeterGaugeChartModel;

@Named
@SessionScoped
public class ChartMB implements Serializable {

    private MeterGaugeChartModel gaugeChartModel;

    @PostConstruct
    public void init() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(20);
                add(50);
                add(120);
                add(220);
            }
        };    
        gaugeChartModel = new MeterGaugeChartModel(30, intervals);
    }

    public MeterGaugeChartModel getGaugeChartModel() {
        return gaugeChartModel;
    }
    
    public void updateGaugeChart() {
        Integer number = gaugeChartModel.getValue().intValue();
        number++;
        gaugeChartModel.setValue(number);
    }
    
    

}
