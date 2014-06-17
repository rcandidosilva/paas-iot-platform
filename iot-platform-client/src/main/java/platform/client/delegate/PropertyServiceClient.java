package platform.client.delegate;

import java.text.MessageFormat;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import platform.api.Property;

/**
 * Jersey REST client generated for REST resource:PropertyService
 *
 * @author rodrigo
 */
public class PropertyServiceClient {

    private WebTarget webTarget;
    private Client client;

    public PropertyServiceClient(String baseUri, String productKey, String deviceKey) {
        client = ClientBuilder.newClient();
        String resourcePath = MessageFormat.format("product/{0}/device/{1}/property",
                new Object[]{productKey, deviceKey});
        webTarget = client.target(baseUri).path(resourcePath);
    }

    public void setResourcePath(String baseUri, String productKey, String deviceKey) {
        String resourcePath = MessageFormat.format("product/{0}/device/{1}/property",
                new Object[]{productKey, deviceKey});
        webTarget = client.target(baseUri).path(resourcePath);
    }

    public Property get(String key) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}",
                new Object[]{key})).request().get(Property.class);
    }

    public String count() throws ClientErrorException {
        return webTarget.path("count").request(
                MediaType.TEXT_PLAIN).get(String.class);
    }

    public void create(Property property) throws ClientErrorException {
        Entity<Property> entity = Entity.entity(property,
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().post(entity, Property.class);
    }

    public void update(Property property) throws ClientErrorException {
        Entity<Property> entity = Entity.entity(property,
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().put(entity);
    }

    public List<Property> list() throws ClientErrorException {
        return webTarget.request().get(new GenericType<List<Property>>() {
        });
    }

    public void delete(String key) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}",
                new Object[]{key})).request().delete();
    }

    public void close() {
        client.close();
    }

}
