package platform.web.ide.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
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
import platform.web.ide.IDEController;
import platform.web.ide.WidgetFactory;
import platform.web.ide.widget.ui.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class LineWidgetController implements WidgetController {

    private String selectedDeviceKey;
    private String selectedPropertyKey;
    private String selectedProductKey;

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
    private WidgetFactory factory;

    @Inject
    private IDEController ide;

    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.LINE);
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setWidget(Widget widget) {
        this.widget = widget;
        if (widget != null) {
            if (widget.getProperties() != null
                    && !widget.getProperties().isEmpty()) {
                Property prop = widget.getProperties().get(0);
                selectedPropertyKey = prop.getKey();
                selectedDeviceKey = prop.getDevice().getKey();
                if (prop.getDevice().getProduct() != null) {
                    selectedProductKey = prop.getDevice().getProduct().getKey();
                }
            }
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

    public void addAction() {
        Property property = propertyService.get(selectedDeviceKey, selectedPropertyKey);
        widget.setProperties(Arrays.asList(new Property[]{property}));
        if (widget.getId() == null) {
            WidgetComponent component = factory.createComponent(widget);
            ide.addWidget(component);
        }
    }
}
