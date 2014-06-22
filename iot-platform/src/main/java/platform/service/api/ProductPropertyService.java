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
import platform.api.Property;

/**
 *
 * @author rodrigo
 */
@Path("/product/{productKey}/device/{deviceKey}/property")
public interface ProductPropertyService {

    @GET
    @Path(value = "count")
    @Produces(value = MediaType.TEXT_PLAIN)
    String count(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey);

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    void create(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey, Property property);

    @DELETE
    @Path(value = "{key}")
    @Consumes(value = MediaType.TEXT_PLAIN)
    void delete(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey, @PathParam(value = "key") String key);

    @GET
    @Path(value = "{key}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.TEXT_PLAIN)
    Property get(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey, @PathParam(value = "key") String key);

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    List<Property> list(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey);

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    void update(@PathParam(value = "productKey") String productKey, @PathParam(value = "deviceKey") String deviceKey, Property property);
    
}
