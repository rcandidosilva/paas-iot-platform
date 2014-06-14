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
import platform.web.widget.UIBarComponent;

/**
 * 
 * @author rodrigo
 */
@Named 
@ViewScoped
public class BarWidgetController implements Serializable {
    
    private String title;
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    private Integer interval;
    
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
        propertiesModel = new ListDataModel<>(new ArrayList<Property>());
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

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public ListDataModel<Property> getPropertiesModel() {
        return propertiesModel;
    }

    public void setPropertiesModel(ListDataModel<Property> propertiesModel) {
        this.propertiesModel = propertiesModel;
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
    
    public void newProperty() {
        Property property = 
                propertyService.get(selectedDeviceKey, selectedPropertyKey);        
        ((List<Property>) propertiesModel.getWrappedData()).add(property);
    }
    
    public void deleteProperty() {
        ((List<Property>) propertiesModel.getWrappedData()).remove(
            propertiesModel.getRowData());
    }    
    
    public void addAction() {
        List<Property> selectedProperties = (List<Property>) 
                propertiesModel.getWrappedData();
        UIBarComponent widget = new UIBarComponent(title, interval, 
                selectedProperties, propertyService);     
        dashboard.addWidget(widget);
    }
}
