package platform.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import platform.api.Device;
import platform.service.DeviceService;

/**
 *
 * @author rodrigo
 */
@Named(value = "deviceMB")
@SessionScoped
public class DeviceMB implements Serializable {
    
    @Inject
    private DeviceService service;
    
    private Device device = new Device();
    private List<Device> deviceList;

    @PostConstruct
    public void init() {
        device = new Device();
        deviceList = new ArrayList<>();
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Device> getDeviceList() {
        return service.list();
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
        
    public void delete(Device device) {
        service.delete(device.getKey());
    }

    public String edit(Device device) {
        this.device = service.get(device.getKey());
        return "edit";
    }

    public String save() {
        service.create(device);
        JSFHelper.addSuccessMessage("Dispositivo salvo com sucesso.");
        return "list";
    }

    public String createNew() {
        device = new Device();
        return "edit";
    }
    
}