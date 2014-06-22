package platform.service.api;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import platform.api.Product;

/**
 *
 * @author rodrigo
 */
@Path("/product")
public interface ProductService {

    @GET
    @Path(value = "count")
    @Produces(value = MediaType.TEXT_PLAIN)
    String count();

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    void create(Product product);

    @DELETE
    @Path(value = "{key}")
    void delete(@PathParam(value = "key") String key);

    @GET
    @Path(value = "{key}")
    @Produces(value = MediaType.APPLICATION_JSON)
    Product get(@PathParam(value = "key") String key);

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    List<Product> list();

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    void update(Product product);
    
}
