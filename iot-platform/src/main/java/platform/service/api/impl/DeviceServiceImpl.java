package platform.service.api.impl;

import platform.service.api.*;
import java.util.Date;
import platform.api.Device;
import java.util.List;
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
import platform.service.PlatformException;

@Component
public class DeviceServiceImpl implements DeviceService {

    @PersistenceContext
    private EntityManager manager;

    private void validateUniqueKey(Device device) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Device> root = query.from(Device.class);
        query.select(root);
        query.where(builder.equal(root.get("key"), device.getKey()));
        List<Device> result = manager.createQuery(query).getResultList();
        if (!result.isEmpty()) {
            for (Device d : result) {
                if (!d.getId().endsWith(device.getId())) {
                    throw new PlatformException("Device key is already been used");
                }
            }
        }
    }

    private void validateUniqueKey2(Device device) {
        List<Device> list = list();
        if (!list.isEmpty()) {
            for (Device d : list) {
                if (d.getKey().equals(device.getKey())
                        && !d.getId().equals(device.getId())) {
                    throw new PlatformException("Device key is already been used");
                }
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Device device) {
        validateUniqueKey(device);
        device.setCreatedAt(new Date());
        device.setUpdatedAt(new Date());
        manager.persist(device);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Device device) {
        validateUniqueKey2(device);
        device.setUpdatedAt(new Date());
        manager.merge(device);
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device get(@PathParam("key") String key) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Device> root = query.from(Device.class);
        query.select(root);
        query.where(builder.equal(root.get("key"), key));
        List<Device> result = manager.createQuery(query).getResultList();
        if (result.isEmpty()) {
            throw new PlatformException("Device key not found");
        }
        return result.get(0);
    }

    @DELETE
    @Path("{key}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("key") String key) {
        manager.remove(get(key));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Device.class));
        return manager.createQuery(query).getResultList();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {
        return String.valueOf(list().size());
    }

}
