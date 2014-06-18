package platform.web.widget;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.map.LatLng;
import platform.api.Device;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.api.DeviceService;
import platform.web.IDEController;
import platform.web.widget.ui.UILocationComponent;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class LocationWidgetController implements WidgetController {

    private String selectedDeviceKey;

    private Widget widget;

    @Inject
    private DeviceService deviceService;

    @Inject
    private IDEController ide;

    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.LOCATION);
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

    public List<Device> getDevices() {
        return deviceService.list();
    }

    public void addAction() {
        Device device = deviceService.get(selectedDeviceKey);
        double lat = device.getLocation().getLatitude();
        double lng = device.getLocation().getLongitude();
        LatLng coord = new LatLng(lat, lng);
        if (widget.getId() == null) {
            UILocationComponent component
                    = new UILocationComponent(widget);
            ide.addWidget(component);
        }
    }

}
