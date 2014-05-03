package platform.service.test.client;

import java.text.MessageFormat;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import platform.api.Device;

/**
 * Jersey REST client generated for REST resource:DeviceService [/device]
 * 
 * @author Rodrigo Cândido da Silva
 */
public class DeviceServiceClient {
    
    private WebTarget webTarget;
    private Client client;

    public DeviceServiceClient(String baseUri) {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseUri).path("device");
    }

    public Device get(String key) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", 
                new Object[]{key})).request().get(Device.class);
    }

    public String count() throws ClientErrorException {
        return webTarget.path("count").request(
                MediaType.TEXT_PLAIN).get(String.class);
    }

    public void create(Device device) throws ClientErrorException {
        Entity<Device> entity = Entity.entity(device, 
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().post(entity, Device.class);
    }

    public void update(Device device) throws ClientErrorException {
        Entity<Device> entity = Entity.entity(device, 
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().put(entity);
    }

    public List<Device> list() throws ClientErrorException {
        return webTarget.request().get(new GenericType<List<Device>>() {});
    }

    public void delete(String key) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}", 
                new Object[]{key})).request().delete();
    }

    public void close() {
        client.close();
    }
    
}
