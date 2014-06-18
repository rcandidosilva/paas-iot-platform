package platform.web.widget;

import java.util.ArrayList;
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
import platform.web.WidgetFactory;
import platform.web.widget.ui.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class BarWidgetController implements WidgetController {

    private String selectedDeviceKey;
    private String selectedPropertyKey;
    private String selectedProductKey;

    private ListDataModel<Property> propertiesModel;

    private Widget widget;

    @Inject
    private DeviceService deviceService;

    @Inject
    private PropertyService propertyService;

    @Inject
    private ProductService productService;

    @Inject
    private ProductDeviceService productDeviceService;

    @Inject
    private IDEController ide;
    
    @Inject
    private WidgetFactory factory;

    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.BAR);
        propertiesModel = new ListDataModel<>(new ArrayList<Property>());
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setWidget(Widget widget) {
        this.widget = widget;
        if (widget != null) {
            propertiesModel = new ListDataModel<>(widget.getProperties());
        }
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

    public ListDataModel<Property> getPropertiesModel() {
        return propertiesModel;
    }

    public void setPropertiesModel(ListDataModel<Property> propertiesModel) {
        this.propertiesModel = propertiesModel;
    }

    public List<Product> getProducts() {
        return productService.list();
    }

    public List<Device> getDevices() {
        if (selectedProductKey != null && !"".equals(selectedProductKey)) {
            return productDeviceService.list(selectedProductKey);
        }
        return deviceService.list();
    }

    public List<Property> getProperties() {
        if (selectedDeviceKey != null && !"".equals(selectedDeviceKey)) {
            return propertyService.list(selectedDeviceKey);
        }
        return new ArrayList<>();
    }

    public void newProperty() {
        Property property
                = propertyService.get(selectedDeviceKey, selectedPropertyKey);
        ((List<Property>) propertiesModel.getWrappedData()).add(property);
    }

    public void deleteProperty() {
        ((List<Property>) propertiesModel.getWrappedData()).remove(
                propertiesModel.getRowData());
    }

    public void addAction() {
        List<Property> properties = (List<Property>) propertiesModel.getWrappedData();
        widget.setProperties(properties);
        if (widget.getId() == null) {
            WidgetComponent component = factory.createComponent(widget);
            ide.addWidget(component);
        }
    }
}
