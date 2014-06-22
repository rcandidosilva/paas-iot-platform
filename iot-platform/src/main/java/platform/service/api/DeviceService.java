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
import platform.api.Device;

/**
 *
 * @author rodrigo
 */
@Path("/device")
public interface DeviceService {

    @GET
    @Path(value = "count")
    @Produces(value = MediaType.TEXT_PLAIN)
    String count();

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    void create(Device device);

    @DELETE
    @Path(value = "{key}")
    @Consumes(value = MediaType.TEXT_PLAIN)
    void delete(@PathParam(value = "key") String key);

    @GET
    @Path(value = "{key}")
    @Produces(value = MediaType.APPLICATION_JSON)
    Device get(@PathParam(value = "key") String key);

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    List<Device> list();

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    void update(Device device);
    
}
