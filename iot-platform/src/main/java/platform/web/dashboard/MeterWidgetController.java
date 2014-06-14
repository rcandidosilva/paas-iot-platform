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
import platform.web.widget.UIMeterComponent;

/**
 * GaugeMeter Widget controller
 * 
 * @author rodrigo
 */
@Named
@ViewScoped
public class MeterWidgetController implements Serializable {
    
    private String title;
    private String label;
    private Double interval;
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    
    private List<Device> devices;
    private ListDataModel<Double> intervalsModel;
    
    @Inject
    private DeviceService deviceService;

    @Inject
    private PropertyService propertyService;
    
    @Inject
    private DashboardController dashboard;
    
    @PostConstruct
    public void init() {
        intervalsModel = new ListDataModel<>(new ArrayList<Double>());
        devices = deviceService.list();        
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
    
    public void newInterval() {
        ((List<Double>) intervalsModel.getWrappedData()).add(interval);
    }
    
    public void deleteInterval() {
        ((List<Double>) intervalsModel.getWrappedData()).remove(
                intervalsModel.getRowData());        
    }
    
    public void addAction() {
        UIMeterComponent widget = new UIMeterComponent(title, label, 
                (List) getIntervalsModel().getWrappedData(), 
                selectedDeviceKey, selectedPropertyKey, propertyService);      
        dashboard.addWidget(widget);
    }
             
}