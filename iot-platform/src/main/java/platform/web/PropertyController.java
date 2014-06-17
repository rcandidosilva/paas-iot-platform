package platform.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Device;
import platform.api.Product;
import platform.api.Property;
import platform.service.api.DeviceService;
import platform.service.api.ProductDeviceService;
import platform.service.api.ProductService;
import platform.service.api.PropertyService;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class PropertyController implements Serializable {

    private Property property = new Property();
    private String selectedDeviceKey;
    private String selectedProductKey;

    @Inject
    private PropertyService propertyService;

    @Inject
    private DeviceService deviceService;

    @Inject
    private ProductService productService;

    @Inject
    private ProductDeviceService productDeviceService;

    @PostConstruct
    public void init() {
        property = new Property();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Property> getPropertyList() {
        return propertyService.listAll();
    }

    public List<Device> getDeviceList() {
        if (selectedProductKey != null && !"".equals(selectedProductKey)) {
            return productDeviceService.list(selectedProductKey);
        } else {
            return deviceService.list();
        }
    }

    public List<Product> getProductList() {
        return productService.list();
    }

    public String getSelectedDeviceKey() {
        return selectedDeviceKey;
    }

    public void setSelectedDeviceKey(String selectedDeviceKey) {
        this.selectedDeviceKey = selectedDeviceKey;
    }

    public String getSelectedProductKey() {
        return selectedProductKey;
    }

    public void setSelectedProductKey(String selectedProductKey) {
        this.selectedProductKey = selectedProductKey;
    }

    public void delete(Property property) {
        propertyService.delete(property.getDevice().getKey(), property.getKey());
    }

    public String edit(Property property) {
        this.property = propertyService.get(property.getDevice().getKey(), property.getKey());
        this.selectedDeviceKey = property.getDevice().getKey();
        return "edit";
    }

    public String save() {
        propertyService.create(selectedDeviceKey, property);
        JSFHelper.addSuccessMessage("Property saved successfuly.");
        return "/pages/property/list";
    }

    public String createNew() {
        property = new Property();
        selectedDeviceKey = null;
        return "/pages/property/edit";
    }

}
