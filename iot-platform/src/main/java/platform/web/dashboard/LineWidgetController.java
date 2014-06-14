package platform.web.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Device;
import platform.api.Property;
import platform.service.DeviceService;
import platform.service.PropertyService;
import platform.web.DashboardController;
import platform.web.widget.UILineComponent;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class LineWidgetController implements Serializable {
   
    private String title;
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    private Integer interval;
    
    private List<Device> devices;
    private List<Property> properties;
        
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

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
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
    
    public void addAction() {
        UILineComponent widget = new UILineComponent(title, selectedDeviceKey, 
                selectedPropertyKey, interval, propertyService);     
        dashboard.addWidget(widget);
    }
}