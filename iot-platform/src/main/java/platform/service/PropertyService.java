package platform.service;

import java.util.ArrayList;
import platform.api.Device;
import platform.api.Property;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import platform.api.PropertyTracking;

/**
 *
 * @author rodrigo
 */
@Path("/device/{deviceKey}/property")
@Stateless
public class PropertyService {
  
    @PersistenceContext
    private EntityManager manager;
    
    @Inject
    private DeviceService deviceService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("deviceKey") String deviceKey, 
            Property property) {
        Device device = getDevice(deviceKey);
        for (Property p : device.getProperties()) {
            if (p.getKey().equals(property.getKey())) {
                throw new PlatformException("Property key is already been used for this device");
            }
        }        
        device.getProperties().add(property);
        deviceService.update(device);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey, 
            Property property) {
        Device device = getDevice(deviceKey);
        Property actual = get(deviceKey, property.getKey());
        if (actual != null) {
            device.getProperties().remove(actual);
            device.getProperties().add(property);          
            deviceService.update(device);
            PropertyTracking tracking = new PropertyTracking(property);
            manager.persist(tracking);
        }
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Property get(@PathParam("deviceKey") String deviceKey, 
            @PathParam("key") String key) {
        Device device = getDevice(deviceKey);
        Property property = null;
        for (Property p : device.getProperties()) {
            if (p.getKey().equals(key)) {
                property = p;
            }
        }
        if (property == null) {
            throw new PlatformException("Property key not found");
        }
        return property;
    } 
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("deviceKey") String deviceKey,
            @PathParam("key") String key) {
        Property property = get(deviceKey, key);
        if (property != null) {
            manager.remove(property);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Property> list(@PathParam("deviceKey") String deviceKey) {
        Device device = deviceService.get(deviceKey);
        if (device != null) {
            return device.getProperties();
        }
        return new ArrayList<>();
    }
    
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String count(@PathParam("deviceKey") String deviceKey) {
        return String.valueOf(list(deviceKey).size());
    }
    
    private Device getDevice(String deviceKey) {
        Device device = deviceService.get(deviceKey);
        if (device == null) {
            throw new PlatformException("Device key not found");
        }
        return device;
    }    
    
}
