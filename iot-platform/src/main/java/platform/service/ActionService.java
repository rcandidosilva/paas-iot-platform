package platform.service;

import java.util.ArrayList;
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
import platform.api.Action;
import platform.api.Device;

/**
 *
 * @author rodrigo
 */
@Path("/device/{deviceKey}/action")
@Stateless
public class ActionService {
  
    @PersistenceContext
    private EntityManager manager;
    
    @Inject
    private DeviceService deviceService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("deviceKey") String deviceKey, 
            Action action) {
        Device device = deviceService.get(deviceKey);
        if (device == null) {
            throw new RuntimeException("Device key not found");
        }
        device.getActions().add(action);
        deviceService.update(device);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey, 
            Action property) {
        // TODO getDevice
        manager.merge(property);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Action get(@PathParam("deviceKey") String deviceKey, 
            @PathParam("name") String name) {
        // TODO getDevice
        return manager.find(Action.class, name);
    } 
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        // getDevice
        manager.remove(get(deviceKey, name));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Action> list(@PathParam("deviceKey") String deviceKey) {
        Device device = deviceService.get(deviceKey);
        if (device != null) {
            return device.getActions();
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
