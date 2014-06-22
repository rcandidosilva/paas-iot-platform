package platform.service.api.impl;

import platform.service.api.*;
import java.util.ArrayList;
import java.util.Date;
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
import platform.api.Device;
import platform.api.Product;
import platform.api.Property;
import platform.api.PropertyTracking;
import platform.service.PlatformException;

/**
 *
 * @author rodrigo
 */
@Component
public class ProductPropertyServiceImpl implements ProductPropertyService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private ProductDeviceServiceImpl productDeviceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            Property property) {
        List<Property> list = list(productKey, deviceKey);
        for (Property p : list) {
            if (p.getKey().equals(property.getKey())) {
                throw new PlatformException("Property key is already been used for this device");
            }
        }
        Device device = productDeviceService.get(productKey, deviceKey);
        property.setTimestamp(new Date());
        property.setDevice(device);
        manager.persist(property);
        PropertyTracking tracking = new PropertyTracking(property);
        manager.persist(tracking);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            Property property) {
        Property actual = get(productKey, deviceKey, property.getKey());
        if (actual != null) {
            actual.setName(property.getName());
            actual.setValue(property.getValue());
            actual.setTimestamp(new Date());
            manager.merge(actual);
        } else {
            Device device = productDeviceService.get(productKey, deviceKey);
            property.setDevice(device);
            property.setTimestamp(new Date());
            manager.persist(property);
        }
        PropertyTracking tracking = new PropertyTracking(property);
        manager.persist(tracking);
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Property get(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            @PathParam("key") String key) {
        List<Property> list = list(productKey, deviceKey);
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
    public void delete(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey,
            @PathParam("key") String key) {
        Property property = get(productKey, deviceKey, key);
        if (property != null) {
            manager.remove(property);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Property> list(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey) {
        Device device = productDeviceService.get(productKey, deviceKey);
        if (device != null) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Property> root = query.from(Property.class);
            query.select(root);
            List<Property> list = manager.createQuery(query).getResultList();
            List<Property> result = new ArrayList<>();
            for (Property property : list) {
                if (property.getDevice() != null) {
                    Product product = property.getDevice().getProduct();
                    if (product != null) {
                        if (product.getKey().equals(productKey)
                                && property.getDevice().getKey().equals(deviceKey)) {
                            result.add(property);
                        }
                    } else {
                        if (property.getDevice().getKey().endsWith(deviceKey)) {
                            result.add(property);
                        }
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
    public String count(@PathParam("productKey") String productKey,
            @PathParam("deviceKey") String deviceKey) {
        return String.valueOf(list(productKey, deviceKey).size());
    }

}
