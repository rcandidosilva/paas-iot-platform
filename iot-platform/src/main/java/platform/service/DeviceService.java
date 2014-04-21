package platform.service;

import platform.api.Device;
import java.util.List;
import javax.ejb.Stateless;
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

@Path("/device")
@Stateless
public class DeviceService {

    @PersistenceContext
    private EntityManager manager;
    
    private void validateUniqueKey(Device device) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Device> root = query.from(Device.class);
        query.select(root);
        query.where(builder.equal(root.get("key"), device.getKey()));
        if (device.getId() != null && !"".equals(device.getId())) {
            query.where(builder.notEqual(root.get("id"), device.getId()));
        }
        if (!manager.createQuery(query).getResultList().isEmpty()) {
            throw new RuntimeException("Device key is already been used");
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Device device) {
        validateUniqueKey(device);
        manager.persist(device);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Device device) {
        validateUniqueKey(device);
        manager.merge(device);
    }
    
    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device get(@PathParam("key") String key) {
        return manager.find(Device.class, key);
    } 
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("id") String key) {
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
