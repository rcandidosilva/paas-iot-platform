package platform.service.api.impl;

import platform.service.api.*;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.stereotype.Component;
import platform.api.Action;
import platform.api.Device;
import platform.service.PlatformException;

/**
 *
 * @author rodrigo
 */
@Component
public class ProductActionServiceImpl implements ProductActionService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private ProductDeviceServiceImpl productDeviceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            Action action) {
        Device device = productDeviceService.get(productKey, deviceKey);
        List<Action> list = list(productKey, deviceKey);
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
    public void update(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            Action action) {
        Device device = productDeviceService.get(productKey, deviceKey);
        Action actual = get(productKey, deviceKey, action.getName());
        if (actual != null) {
            manager.remove(actual);
            action.setDevice(device);
            manager.persist(action);
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Action get(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        List<Action> list = list(productKey, deviceKey);
        for (Action action : list) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
    }

    @DELETE
    @Path("{name}")
    public void delete(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            @PathParam("name") String name) {
        Action action = get(productKey, deviceKey, name);
        if (action != null) {
            manager.remove(action);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Action> list(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey) {
        Device device = productDeviceService.get(productKey, deviceKey);
        if (device != null) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Action> root = query.from(Action.class);
            query.select(root);
            List<Action> list = manager.createQuery(query).getResultList();
            List<Action> result = new ArrayList<>();
            for (Action action : list) {
                if (action.getDevice().getKey().endsWith(deviceKey)) {
                    result.add(action);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String count(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey) {
        return String.valueOf(list(productKey, deviceKey).size());
    }
    
}
