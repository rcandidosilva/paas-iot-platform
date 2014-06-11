/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package platform.web.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Device;
import platform.api.Property;
import platform.service.DeviceService;
import platform.service.PropertyService;
import platform.web.DashboardController;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class PieChartController implements Serializable {
    
    private String title;
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    
    private List<Device> devices;
    private List<Property> properties;
    
    private ListDataModel<Property> propertiesModel;
    
    @Inject
    private DeviceService deviceService;
    @Inject
    private PropertyService propertyService;
    
    @Inject
    private DashboardController dashboard;
    
    @PostConstruct
    public void init() {
        devices = deviceService.list();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedDeviceKey() {
        return selectedDeviceKey;
    }

    public void setSelectedDeviceKey(String selectedDeviceKey) {
        this.selectedDeviceKey = selectedDeviceKey;
    }

    public String getSelectedPropertyKey() {
        return selectedPropertyKey;
    }

    public void setSelectedPropertyKey(String selectedPropertyKey) {
        this.selectedPropertyKey = selectedPropertyKey;
    }
    
    public List<Device> getDevices() {
        return devices;
    }
    
    public List<Property> getProperties() {
        if (selectedDeviceKey != null && !"".equals(selectedDeviceKey)) {
            return propertyService.list(selectedDeviceKey);
        }
        return new ArrayList<>();
    }

    public ListDataModel<Property> getPropertiesModel() {
        return propertiesModel;
    }

    public void setPropertiesModel(ListDataModel<Property> propertiesModel) {
        this.propertiesModel = propertiesModel;
    }
                
    public void addAction() {
//        GaugeMeterWidget widget = 
//                new GaugeMeterWidget((List) getIntervalsModel().getWrappedData(), 
//                        label, title, selectedDeviceKey, selectedPropertyKey);        
//        dashboard.addWidget(widget);
    }
    
}
