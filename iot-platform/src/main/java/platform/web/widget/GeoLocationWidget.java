package platform.web.widget;

import java.util.Date;
import java.util.List;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.component.gmap.GMap;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.poll.Poll;
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
public class GeoLocationWidget implements WidgetComponent {

    private Panel panel;

    private String title;
    private LatLng defaultCoordinate;
    private List<LatLng> coordinates;
    
    public GeoLocationWidget() {
        super();
    }
    
    public GeoLocationWidget(String title, LatLng defaultCoordinate, 
            List<LatLng> coordinates) {
        this.title = title;
        this.defaultCoordinate = defaultCoordinate;
        this.coordinates = coordinates;
    }
    
    @Override
    public Panel create(String widgetId) {
        String mapId = "geoLocation_" + System.currentTimeMillis();

        GMap gmap = new GMap();
        gmap.setId(mapId);
        gmap.setCenter(defaultCoordinate.getLat() + 
                "," + defaultCoordinate.getLng());
        gmap.setZoom(13);
        gmap.setType("HYBRID");
        gmap.setStyle("width: 300px; height: 250px;");
        MapModel mapModel = new DefaultMapModel();
        for (LatLng coord : coordinates) {
            mapModel.addOverlay(new Marker(coord));
        }
        gmap.setModel(mapModel);
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        panel = (Panel) application.createComponent(Panel.COMPONENT_TYPE);
        panel.setId(widgetId);
        panel.setHeader(title);
        panel.setClosable(true);
        panel.setToggleable(true);
        panel.setStyle("width: 400px; height: 300px;");
        
        Poll ajaxPoll = new Poll();
        ajaxPoll.setInterval(3);
        ajaxPoll.setUpdate(mapId);
        MethodExpression expression =  
                ExpressionFactory.newInstance().createMethodExpression(
                context.getELContext(), 
                "#{geoLocationWidget.updateMap}", 
                null, new Class[0]);
        ajaxPoll.setListener(expression);
                
        panel.getChildren().add(gmap);
        panel.getChildren().add(ajaxPoll);
        return panel;
    
    }
    
    public void updateMap() {
        System.out.println("updateMap executed at " + new Date());
    }   

    @Override
    public void update() {
        System.out.println("updateMap executed at " + new Date());
    }
    
}