package platform.service;

import java.util.Date;
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
import platform.api.Device;
import platform.api.Product;

@Path("/product")
@Stateless
public class ProductService {

    @PersistenceContext
    private EntityManager manager;

    private void validateUniqueKey(Product product) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Product> root = query.from(Product.class);
        query.select(root);
        query.where(builder.equal(root.get("key"), product.getKey()));
        if (product.getId() != null && !"".equals(product.getId())) {
            query.where(builder.notEqual(root.get("id"), product.getId()));
        }
        if (!manager.createQuery(query).getResultList().isEmpty()) {
            throw new PlatformException("Product key is already been used");
        }
    }

    @POST
    @Consumes("text/json")
    public void create(Product product) {
        validateUniqueKey(product);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        manager.persist(product);
    }

    @PUT
    @Consumes("text/json")
    public void update(Product product) {
        validateUniqueKey(product);
        product.setUpdatedAt(new Date());
        manager.merge(product);
    }

    @GET
    @Path("{key}")
    @Produces("text/json")
    public Product get(@PathParam("key") String key) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Device> root = query.from(Device.class);
        query.select(root);
        query.where(builder.equal(root.get("key"), key));
        List<Product> result = manager.createQuery(query).getResultList();
        if (result.isEmpty()) {
            throw new PlatformException("Product key not found");
        }
        return result.get(0);
    }

    @DELETE
    @Path("{key}")
    public void delete(@PathParam("key") String key) {
        manager.remove(get(key));
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
