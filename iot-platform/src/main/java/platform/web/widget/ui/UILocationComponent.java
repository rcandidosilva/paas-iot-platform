package platform.web.widget.ui;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.primefaces.component.gmap.GMap;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import platform.model.Widget;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
@Named
@Dependent
public class UILocationComponent implements WidgetComponent {

    private Panel panel;

    private String widgetId;

    private LatLng defaultCoordinate;
    private List<LatLng> coordinates;

    private Widget widget;

    public UILocationComponent(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String getTitle() {
        return widget.getTitle();
    }

    @Override
    public Object createComponent(String widgetId) {
        this.widgetId = widgetId;

        GMap gmap = new GMap();
        gmap.setCenter(defaultCoordinate.getLat()
                + "," + defaultCoordinate.getLng());

        gmap.setZoom(13);
        gmap.setType("HYBRID");
        gmap.setStyle("width:600px; height:400px");
        MapModel mapModel = new DefaultMapModel();

        for (LatLng coord : coordinates) {
            mapModel.addOverlay(new Marker(coord));
        }

        gmap.setModel(mapModel);

        return gmap;

    }

    @Override
    public void update() {
        System.out.println("updateMap executed at " + new Date());
    }

    @Override
    public String getType() {
        return WidgetType.LOCATION;
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setService(Object service) {
        // TODO
    }

    @Override
    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public String getWidgetId() {
        return widgetId;
    }

}
