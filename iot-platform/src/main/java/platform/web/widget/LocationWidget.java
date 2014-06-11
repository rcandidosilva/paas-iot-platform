package platform.web.widget;

import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.component.gmap.GMap;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class LocationWidget implements WidgetComponent {

    private Panel panel;

    private String title;
    private LatLng defaultCoordinate;
    private List<LatLng> coordinates;
    
    public LocationWidget() {
        super();
    }
    
    public LocationWidget(String title, LatLng defaultCoordinate, 
            List<LatLng> coordinates) {
        this.title = title;
        this.defaultCoordinate = defaultCoordinate;
        this.coordinates = coordinates;
    }

    @Override
    public String getTitle() {
        return title;
    }
        
    @Override
    public Object create(String widgetId) {        
        GMap gmap = new GMap();
        gmap.setCenter(defaultCoordinate.getLat() + 
                "," + defaultCoordinate.getLng());

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
    
}