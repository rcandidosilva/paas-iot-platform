package platform.service.api;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
import platform.service.PlatformException;

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
        List<Action> list = list(deviceKey);
        for (Action a : list) {
            if (a.getName().equals(action.getName())) {
                throw new PlatformException("Action name is already been used for this device");
            }
        }
        action.setDevice(device);
        manager.persist(action);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey,
            Action action) {
        Device device = getDevice(deviceKey);
        Action actual = get(deviceKey, action.getName());
        if (actual != null) {
            manager.remove(actual);
            action.setDevice(device);
            manager.persist(action);
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Action get(@PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        List<Action> list = list(deviceKey);
        for (Action action : list) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
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

    public void delete(String name) {
        List<Action> actions = listAll();
        for (Action action : actions) {
            if (action.getName().equals(name)) {
                manager.remove(action);
            }
        }
    }

    public List<Action> listAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Action> root = query.from(Action.class);
        query.select(root);
        return manager.createQuery(query).getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Action> list(@PathParam("deviceKey") String deviceKey) {
        Device device = getDevice(deviceKey);
        if (device != null) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Action> root = query.from(Action.class);
            query.select(root);
            //query.where(builder.equal(root.get("device").get("id"), device));
            List<Action> list = manager.createQuery(query).getResultList();
            List<Action> result = new ArrayList<>();
            for (Action action : list) {
                if (action.getDevice() != null) {
                    if (action.getDevice().getKey().endsWith(deviceKey)) {
                        result.add(action);
                    }
                }
            }
            return result;
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
