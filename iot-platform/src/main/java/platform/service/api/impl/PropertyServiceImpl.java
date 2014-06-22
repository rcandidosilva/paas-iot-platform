package platform.service.api.impl;

import platform.service.api.*;
import java.util.ArrayList;
import platform.api.Device;
import platform.api.Property;
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
import platform.api.PropertyTracking;
import platform.service.PlatformException;

/**
 *
 * @author rodrigo
 */
@Component
public class PropertyServiceImpl implements PropertyService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private DeviceServiceImpl deviceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("deviceKey") String deviceKey,
            Property property) {
        Device device = getDevice(deviceKey);
        List<Property> list = list(deviceKey);
        for (Property p : list) {
            if (p.getKey().equals(property.getKey())) {
                throw new PlatformException("Property key is already been used for this device");
            }
        }
        property.setDevice(device);
        manager.persist(property);
        PropertyTracking tracking = new PropertyTracking(property);
        manager.persist(tracking);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("deviceKey") String deviceKey,
            Property property) {
        Device device = getDevice(deviceKey);
        Property actual = get(deviceKey, property.getKey());
        if (actual != null) {
            manager.remove(actual);
            property.setDevice(device);
            manager.persist(property);
            PropertyTracking tracking = new PropertyTracking(property);
            manager.persist(tracking);
        }
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Property get(@PathParam("deviceKey") String deviceKey,
            @PathParam("key") String key) {
        List<Property> list = list(deviceKey);
        for (Property property : list) {
            if (property.getKey().equals(key)) {
                return property;
            }
        }
        return null;
    }

    @DELETE
    @Path("{key}")
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
        Device device = getDevice(deviceKey);
        if (device != null) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Property> root = query.from(Property.class);
            query.select(root);
            //query.where(builder.equal(root.get("device").get("id"), device));
            List<Property> list = manager.createQuery(query).getResultList();
            List<Property> result = new ArrayList<>();
            for (Property property : list) {
                if (property.getDevice().getKey().endsWith(deviceKey)) {
                    result.add(property);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }

    public List<Property> listAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Property> root = query.from(Property.class);
        query.select(root);
        //query.where(builder.equal(root.get("device").get("id"), device));
        return manager.createQuery(query).getResultList();
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
