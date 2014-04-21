package platform.service;

import java.util.ArrayList;
import platform.api.Device;
import platform.api.Property;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rodrigo
 */
@Path("/device/{deviceKey}/properties")
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
        Device device = deviceService.get(deviceKey);
        if (device == null) {
            throw new RuntimeException("Device key not found");
        }
        device.getProperties().add(property);
        deviceService.update(device);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey, 
            Property property) {
        // TODO getDevice
        manager.merge(property);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Property get(@PathParam("deviceKey") String deviceKey, 
            @PathParam("key") String key) {
        // TODO getDevice
        return manager.find(Property.class, key);
    } 
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("deviceKey") String deviceKey,
            @PathParam("key") String key) {
        // getDevice
        manager.remove(get(deviceKey, key));
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
    
}
