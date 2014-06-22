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
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
public class SamplesController implements Serializable {

    private MeterGaugeChartModel gaugeChartModel;
    private CartesianChartModel barChartModel;
    
    private int count;
    

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
        
        createBarChartModel();
    }

    public MeterGaugeChartModel getGaugeChartModel() {
        return gaugeChartModel;
    }

    public void updateGaugeChart() {
        Integer number = gaugeChartModel.getValue().intValue();
        number++;
        gaugeChartModel.setValue(number);
    }

    public CartesianChartModel getBarChartModel() {
        return barChartModel;
    }
    
    

    private void createBarChartModel() {
        barChartModel = new CartesianChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);

        barChartModel.addSeries(boys);
        barChartModel.addSeries(girls);

    }

}
