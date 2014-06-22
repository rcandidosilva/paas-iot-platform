/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platform.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author rodrigo
 */
@Controller
@Scope("session")
public class SampleDashboardController implements Serializable {

    private DashboardModel model;
    private Dashboard dashboard;

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
    
    @PostConstruct
    public void init() {
        
        dashboard = new Dashboard();
        
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();

        column1.addWidget("sports");
        column1.addWidget("finance");

        column2.addWidget("lifestyle");
        column2.addWidget("weather");

        column3.addWidget("politics");

        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        
        dashboard.setModel(model);
        
        createPanels();
    }
    
    
    public void createPanels() {
        
        Panel panel = (Panel) JSFHelper.createComponent(Panel.COMPONENT_TYPE);
        panel.setId("widget2");
        panel.setHeader("widget2");
        panel.setClosable(true);
        panel.setToggleable(true);
        panel.setStyle("width: 400px; height: 330px;");
        dashboard.getChildren().add(panel);
        
        Panel panel2 = (Panel) JSFHelper.createComponent(Panel.COMPONENT_TYPE);
        panel2.setId("widget3");
        panel2.setHeader("widget3");
        panel2.setClosable(true);
        panel2.setToggleable(true);
        panel2.setStyle("width: 400px; height: 330px;");
        dashboard.getChildren().add(panel2);
        
        model.getColumn(0).addWidget(panel.getId());
        model.getColumn(2).addWidget(panel2.getId());
    }
    public void handleReorder(DashboardReorderEvent event) {
        
        
        
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());

        addMessage(message);
    }

    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");

        addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public DashboardModel getModel() {
        return model;
    }
    
    public void test() {
        System.out.println("Testing...");
    }
            

}
