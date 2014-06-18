package platform.web.widget;

import java.util.ArrayList;
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
import platform.service.api.DeviceService;
import platform.service.api.PropertyService;
import platform.web.IDEController;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class PieWidgetController implements WidgetController {
    
    private String selectedDeviceKey;
    private String selectedPropertyKey;
    
    private ListDataModel<Property> propertiesModel;
    
    private Widget widget;
    
    @Inject
    private DeviceService deviceService;
    @Inject
    private PropertyService propertyService;
    
    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.PIE);
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
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
    
    public List<Device> getDevices() {
        return deviceService.list();
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
