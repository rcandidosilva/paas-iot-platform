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
import platform.api.Product;
import platform.api.Property;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.api.DeviceService;
import platform.service.api.ProductDeviceService;
import platform.service.api.ProductService;
import platform.service.api.PropertyService;
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
    private String selectedProductKey;
    
    private ListDataModel<Double> intervalsModel;
    
    @Inject
    private DeviceService deviceService;

    @Inject
    private PropertyService propertyService;
    
    @Inject
    private ProductService productService;
    
    @Inject
    private ProductDeviceService productServiceService;
    
    @Inject
    private IDEController ide;
    
    @Inject
    private WidgetComponentFactory factory;
    
    @PostConstruct
    public void init() {
        widget = new Widget(WidgetType.METER);
        intervalsModel = new ListDataModel<>(new ArrayList<Double>());     
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

    public String getSelectedProductKey() {
        return selectedProductKey;
    }

    public void setSelectedProductKey(String selectedProductKey) {
        this.selectedProductKey = selectedProductKey;
    }
    
    public List<Product> getProducts() {
        return productService.list();
    }
    
    public List<Device> getDevices() {
        if (selectedProductKey != null && !"".equals(selectedProductKey)) {
            return productServiceService.list(selectedProductKey);
        }
        return deviceService.list();
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