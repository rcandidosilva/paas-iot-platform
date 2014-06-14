package platform.web.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Device;
import platform.api.Property;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.DeviceService;
import platform.service.PropertyService;
import platform.web.IDEController;
import platform.web.widget.UIBarComponent;

/**
 * 
 * @author rodrigo
 */
@Named 
@ViewScoped
public class BarWidgetController implements Serializable {
    
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    private Double interval;
    
    private List<Device> devices;
    private List<Property> properties;
    
    private ListDataModel<Property> propertiesModel;
        
    private Widget widget;
    
    @Inject
    private DeviceService deviceService;
    
    @Inject
    private PropertyService propertyService;
    
    @Inject
    private IDEController ide;
    
    @PostConstruct
    public void init() {
        widget = new Widget(WidgetType.BAR);
        devices = deviceService.list();
        propertiesModel = new ListDataModel<>(new ArrayList<Property>());
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
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

    public Double getInterval() {
        return interval;
    }

    public void setInterval(Double interval) {
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
        List<Property> properties = (List<Property>) 
                propertiesModel.getWrappedData();
        widget.setProperties(properties);
        widget.setIntervals(Arrays.asList(new Double[] {interval}));
        UIBarComponent component = new UIBarComponent(widget, propertyService);     
        ide.addWidget(component);
    }
}
