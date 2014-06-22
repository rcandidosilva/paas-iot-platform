package platform.web.ide.widget;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.model.map.LatLng;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import platform.api.Device;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.api.DeviceService;
import platform.web.ide.IDEController;
import platform.web.ide.widget.ui.UILocationComponent;

/**
 *
 * @author rodrigo
 */
@Controller
@Scope("session")
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
