package platform.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import platform.api.Device;
import platform.api.Product;
import platform.service.api.DeviceService;
import platform.service.api.ProductService;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class DeviceController implements Serializable {

    private static Logger logger = Logger.getLogger(DeviceController.class);

    private Device device = new Device();
    private String selectedProductKey;
    private String selectedParentKey;

    @Inject
    private DeviceService deviceService;

    @Inject
    private ProductService productService;

    @PostConstruct
    public void init() {
        device = new Device();
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Device> getDeviceList() {
        return deviceService.list();
    }

    public List<Product> getProductList() {
        return productService.list();
    }

    public String getSelectedProductKey() {
        return selectedProductKey;
    }

    public void setSelectedProductKey(String selectedProductKey) {
        this.selectedProductKey = selectedProductKey;
    }

    public String getSelectedParentKey() {
        return selectedParentKey;
    }

    public void setSelectedParentKey(String selectedParentKey) {
        this.selectedParentKey = selectedParentKey;
    }

    public void delete(Device device) {
        deviceService.delete(device.getKey());
    }

    public String edit(Device device) {
        this.device = deviceService.get(device.getKey());
        if (device.getProduct() != null) {
            selectedProductKey = device.getProduct().getKey();
        }
        if (device.getParent() != null) {
            selectedParentKey = device.getParent().getKey();
        }
        return "/pages/device/edit";
    }

    public String save() {
        try {
            if (selectedProductKey != null && !"".equals(selectedProductKey)) {
                Product product = productService.get(selectedProductKey);
                device.setProduct(product);
            }
            if (selectedParentKey != null && !"".equals(selectedParentKey)) {
                Device parent = deviceService.get(selectedParentKey);
                device.setParent(parent);
            }
            deviceService.create(device);
            JSFHelper.addSuccessMessage("Device saved successfully");
            logger.debug("Device '" + device.getKey() + "' saved successfully.");
            return "/pages/device/list";
        } catch (Exception ex) {
            JSFHelper.addErrorMessage(ex.getMessage());
            logger.error("Error to save the device '" + device.getKey() + "'", ex);
            return "/pages/device/edit";
        }
    }

    public String createNew() {
        device = new Device();
        selectedProductKey = null;
        selectedParentKey = null;
        return "/pages/device/edit";
    }

}
