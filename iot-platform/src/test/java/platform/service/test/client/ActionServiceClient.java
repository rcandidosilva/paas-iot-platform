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
import platform.api.Action;

/**
 * Jersey REST client generated for REST resource:ActionService
 *
 * @author rodrigo
 */
public class ActionServiceClient {

    private WebTarget webTarget;
    private Client client;

    public ActionServiceClient(String baseUri, String deviceKey) {
        client = ClientBuilder.newClient();
        String resourcePath = MessageFormat.format("device/{0}/action", new Object[]{deviceKey});
        webTarget = client.target(baseUri).path(resourcePath);
    }

    public void setResourcePath(String baseUri, String deviceKey) {
        String resourcePath = MessageFormat.format("device/{0}/action", new Object[]{deviceKey});
        webTarget = client.target(baseUri).path(resourcePath);
    }

    public Action get(String key) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", 
                new Object[]{key})).request().get(Action.class);
    }

    public String count() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public void create(Action action) throws ClientErrorException {
        Entity<Action> entity = Entity.entity(action,
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().post(entity, Action.class);
    }

    public void update(Action action) throws ClientErrorException {
        Entity<Action> entity = Entity.entity(action,
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().put(entity);
    }

    public List<Action> list() throws ClientErrorException {
        return webTarget.request().get(new GenericType<List<Action>>() {
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
