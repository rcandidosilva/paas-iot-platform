package platform.service.api;

import java.util.ArrayList;
import java.util.Date;
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
import platform.api.Device;
import platform.api.Product;
import platform.service.PlatformException;

/**
 *
 * @author rodrigo
 */
@Path("/product/{productKey}/device")
@Stateless
public class ProductDeviceService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private ProductService productService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("productKey") String productKey,
            Device device) {
        Product product = getProduct(productKey);

        List<Device> list = list(productKey);
        for (Device d : list) {
            if (d.getKey().equals(device.getKey())) {
                throw new PlatformException("Property key is already been used for this device");
            }
        }
        device.setProduct(product);
        manager.persist(device);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("productKey") String productKey,
            Device device) {
        //validateUniqueKey2(device);
        device.setUpdatedAt(new Date());
        manager.merge(device);
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Device get(@PathParam("productKey") String productKey,
            @PathParam("key") String key) {
        List<Device> list = list(productKey);
        for (Device device : list) {
            if (device.getKey().equals(key)) {
                return device;
            }
        }
        return null;
    }

    @DELETE
    @Path("{key}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("productKey") String productKey,
            @PathParam("key") String key) {
        Device device = get(productKey, key);
        if (device != null) {
            manager.remove(device);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> list(@PathParam("productKey") String productKey) {
        Product product = getProduct(productKey);
        if (product != null) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Device> root = query.from(Device.class);
            query.select(root);
            //query.where(builder.equal(root.get("device").get("id"), device));
            List<Device> list = manager.createQuery(query).getResultList();
            List<Device> result = new ArrayList<>();
            for (Device device : list) {
                if (device.getProduct() != null) {
                    if (device.getProduct().getKey().endsWith(productKey)) {
                        result.add(device);
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
    public String count(@PathParam("productKey") String productKey) {
        return String.valueOf(list(productKey).size());
    }

    private Product getProduct(String productKey) {
        Product product = productService.get(productKey);
        if (product == null) {
            throw new PlatformException("Product key not found");
        }
        return product;
    }
}
