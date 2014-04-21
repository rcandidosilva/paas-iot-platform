package platform.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import platform.api.Product;

@Path("/product")
@Stateless
public class ProductService {   
    
    @PersistenceContext
    private EntityManager manager;
    
    @POST
    @Consumes("text/json")
    public void create(Product product) {
        manager.persist(product);
    }
    
    @PUT
    @Consumes("text/json")
    public void update(Product product) {
        manager.merge(product);
    }
    
    @GET
    @Path("{id}")
    @Produces("text/json")
    public Product get(@PathParam("id") String id) {
        return manager.find(Product.class, id);
    } 
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        manager.remove(get(id));
    }
    
    @GET
    @Produces("text/json")
    public List<Product> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Product.class));
        return manager.createQuery(query).getResultList();
    }
    
    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(list().size());
    }
}
