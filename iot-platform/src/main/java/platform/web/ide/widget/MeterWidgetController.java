package platform.web.ide.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
import platform.web.ide.widget.ui.WidgetComponent;
import platform.web.ide.WidgetFactory;

/**
 * GaugeMeter Widget controller
 *
 * @author rodrigo
 */
@Controller
@Scope("session")
public class MeterWidgetController implements WidgetController {

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
    private WidgetFactory factory;

    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.METER);
        intervalsModel = new ListDataModel<>(new ArrayList<Double>());
        interval = null;
        selectedDeviceKey = null;
        selectedPropertyKey = null;
        selectedProductKey = null;
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setWidget(Widget widget) {
        this.widget = widget;
        if (widget != null) {
            intervalsModel = new ListDataModel<>(widget.getIntervals());
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
        widget.setProperties(Arrays.asList(new Property[]{property}));
        if (widget.getId() == null) {
            WidgetComponent component = factory.createComponent(widget);
            ide.addWidget(component);
        }
    }

}
