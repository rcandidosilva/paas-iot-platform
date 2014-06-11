package platform.web.widget.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.map.LatLng;
import platform.api.Device;
import platform.service.DeviceService;
import platform.web.DashboardController;
import platform.web.widget.GeoLocationWidget;

@Named
@ViewScoped
public class GeoLocationController implements Serializable {

    private String title;
    private String selectedDeviceKey;

    private List<Device> devices;

    @Inject
    private DeviceService deviceService;
    
    @Inject
    private DashboardController dashboard;

    @PostConstruct
    public void init() {
        devices = deviceService.list();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedDeviceKey() {
        return selectedDeviceKey;
    }

    public void setSelectedDeviceKey(String selectedDeviceKey) {
        this.selectedDeviceKey = selectedDeviceKey;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void addAction() {
        Device device = deviceService.get(selectedDeviceKey);
        double lat = device.getLocation().getLatitude();
        double lng = device.getLocation().getLongitude();
        LatLng coord = new LatLng(lat, lng);
        
        GeoLocationWidget widget
                = new GeoLocationWidget(title, coord, new ArrayList<LatLng>());
        dashboard.addWidget(widget);
    }

}
