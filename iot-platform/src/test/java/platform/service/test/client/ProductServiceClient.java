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
import platform.api.Product;

/**
 * Jersey REST client generated for REST resource:ProductService [/device]
 * 
 * @author Rodrigo Cândido da Silva
 */
public class ProductServiceClient {
    
    private final WebTarget webTarget;
    private final Client client;

    public ProductServiceClient(String baseUri) {
        client = ClientBuilder.newClient();
        webTarget = client.target(baseUri).path("product");
    }

    public Product get(String key) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", 
                new Object[]{key})).request().get(Product.class);
    }

    public String count() throws ClientErrorException {
        return webTarget.path("count").request(
                MediaType.TEXT_PLAIN).get(String.class);
    }

    public void create(Product product) throws ClientErrorException {
        Entity<Product> entity = Entity.entity(product, 
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().post(entity, Product.class);
    }

    public void update(Product product) throws ClientErrorException {
        Entity<Product> entity = Entity.entity(product, 
                MediaType.APPLICATION_JSON_TYPE);
        webTarget.request().put(entity);
    }

    public List<Product> list() throws ClientErrorException {
        return webTarget.request().get(new GenericType<List<Product>>() {});
    }

    public void delete(String key) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}", 
                new Object[]{key})).request().delete();
    }

    public void close() {
        client.close();
    }
    
}
