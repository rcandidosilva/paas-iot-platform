package client;

import java.text.MessageFormat;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:DeviceService [/device]
 * 
 */
public class DeviceServiceClient {
    
    private WebTarget webTarget;
    private Client client;

    public DeviceServiceClient(String baseUri) {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseUri).path("device");
    }

    public <T> T get(Class<T> responseType, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", 
                new Object[]{id})).request().get(responseType);
    }

    public String count() throws ClientErrorException {
        return webTarget.path("count").request(
                MediaType.TEXT_PLAIN).get(String.class);
    }

    public void create() throws ClientErrorException {
        webTarget.request().post(null);
    }

    public void update() throws ClientErrorException {
        webTarget.request().put(null);
    }

    public <T> T list(Class<T> responseType) throws ClientErrorException {
        return webTarget.request().get(responseType);     
    }

    public <T> T delete(Class<T> responseType, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", 
                new Object[]{id})).request().get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
