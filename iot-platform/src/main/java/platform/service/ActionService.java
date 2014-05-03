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
        Device device = getDevice(deviceKey);
        for (Action a : device.getActions()) {
            if (a.getName().equals(action.getName())) {
                throw new PlatformException("Action name is already been used for this device");
            }
        }         
        device.getActions().add(action);
        deviceService.update(device);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey,
            Action action) {
        Device device = getDevice(deviceKey);
        Action actual = get(deviceKey, action.getName());
        if (actual != null) {
            device.getActions().remove(actual);    
            deviceService.update(device);
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Action get(@PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        Device device = getDevice(deviceKey);
        Action action = null;
        for (Action a : device.getActions()) {
            if (a.getName().equals(name)) {
                action = a;
            }
        }
        if (action == null) {
            throw new PlatformException("Action name not found");
        }
        return action;
    }

    @DELETE
    @Path("{name}")
    public void delete(@PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        Action action = get(deviceKey, name);
        if (action != null) {
            manager.remove(action);
        }
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

    private Device getDevice(String deviceKey) {
        Device device = deviceService.get(deviceKey);
        if (device == null) {
            throw new PlatformException("Device key not found");
        }
        return device;
    }

}
