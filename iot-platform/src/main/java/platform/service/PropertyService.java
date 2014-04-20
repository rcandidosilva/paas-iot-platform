package platform.service;

import api.Device;
import api.Property;
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

/**
 *
 * @author rodrigo
 */
@Path("/device/{deviceId}/properties")
public class PropertyService {
  
    @PersistenceContext
    private EntityManager manager;
    
    @POST
    @Consumes("text/json")
    public void create(@PathParam("deviceId") int deviceId, 
            Property property) {
        // TODO getDevice
        manager.persist(property);
    }
    
    @PUT
    @Consumes("text/json")
    public void update(int deviceId, Property property) {
        // TODO getDevice
        manager.merge(property);
    }
    
    @GET
    @Path("{id}")
    @Produces("text/json")
    public Property get(@PathParam("deviceId") int deviceId, 
            @PathParam("id") int id) {
        // TODO getDevice
        return manager.find(Property.class, id);
    } 
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("deviceId") int deviceId,
            @PathParam("id") int id) {
        // getDevice
        manager.remove(get(deviceId, id));
    }
    
    @GET
    @Produces("text/json")
    public List<Device> list(@PathParam("deviceId") int deviceId) {
        // TODO getDevice
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Device.class));
        return manager.createQuery(query).getResultList();
    }
    
    @GET
    @Path("count")
    @Produces("text/plain")
    public String count(@PathParam("deviceId") int deviceId) {
        // TODO getDevice
        CriteriaQuery cq = manager.getCriteriaBuilder().createQuery();
        Root rt = cq.from(Device.class);
        cq.select(manager.getCriteriaBuilder().count(rt));
        return String.valueOf(manager.createQuery(cq).getSingleResult());
    }
    
}
