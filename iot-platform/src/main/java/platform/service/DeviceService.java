package platform.service;

import api.Device;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

@Path("/device")
public class DeviceService {

    @PersistenceContext
    private EntityManager manager;
    
    @POST
    @Consumes("text/json")
    public void create(Device device) {
        manager.persist(device);
    }
    
    @PUT
    @Consumes("text/json")
    public void update(Device device) {
        manager.merge(device);
    }
    
    @GET
    @Path("{id}")
    @Produces("text/json")
    public Device get(@PathParam("id") int id) {
        return manager.find(Device.class, id);
    } 
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        manager.remove(get(id));
    }
    
    @GET
    @Produces("text/json")
    public List<Device> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Device.class));
        return manager.createQuery(query).getResultList();
    }
    
    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        CriteriaQuery cq = manager.getCriteriaBuilder().createQuery();
        Root rt = cq.from(Device.class);
        cq.select(manager.getCriteriaBuilder().count(rt));
        return String.valueOf(manager.createQuery(cq).getSingleResult());
    }
    
}
