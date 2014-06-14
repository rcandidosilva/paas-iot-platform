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
import platform.web.widget.WidgetComponent;
import platform.web.widget.WidgetComponentFactory;

/**
 * GaugeMeter Widget controller
 * 
 * @author rodrigo
 */
@Named
@ViewScoped
public class MeterWidgetController implements Serializable {
    
    private Widget widget;
    
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
    private IDEController ide;
    
    @Inject
    private WidgetComponentFactory factory;
    
    @PostConstruct
    public void init() {
        widget = new Widget(WidgetType.METER);
        intervalsModel = new ListDataModel<>(new ArrayList<Double>());
        devices = deviceService.list();        
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
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
        List<Double> intervals = (List) getIntervalsModel().getWrappedData();
        Property property = propertyService.get(selectedDeviceKey, selectedPropertyKey);
        widget.setIntervals(intervals);
        widget.setProperties(Arrays.asList(new Property[] {property}));
        WidgetComponent component = factory.createComponent(widget);
        ide.addWidget(component);
    }
             
}