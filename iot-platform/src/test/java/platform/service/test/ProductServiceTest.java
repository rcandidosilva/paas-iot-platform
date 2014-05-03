package platform.service.test;

import java.util.List;
import javax.ws.rs.BadRequestException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import platform.api.Product;
import platform.service.test.client.ProductServiceClient;

/**
 *
 * @author Rodrigo Cândido da Silva
 */
public class ProductServiceTest {

    private static final String SERVICE_URL
            = "http://127.0.0.1:8080/iot-platform/rest/";

    private static ProductServiceClient service;

    @BeforeClass
    public static void start() {
        service = new ProductServiceClient(SERVICE_URL);
    }

    @AfterClass
    public static void end() {
        service.close();
    }

    @Test
    public void testGetProductSuccessful() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = new Product(key,
                "testProduct", "testProduct");
        service.create(product);
        Product loaded = service.get(key);
        Assert.assertNotNull(loaded);
        Assert.assertNotNull(loaded.getKey());
        Assert.assertTrue(loaded.getKey().equals(key));
        Assert.assertNotNull(loaded.getName());
        Assert.assertTrue(loaded.getName().equals("testProduct"));
        Assert.assertNotNull(loaded.getDescription());
        Assert.assertTrue(loaded.getDescription().equals("testProduct"));
    }

    @Test(expected = BadRequestException.class)
    public void testGetProductNotFound() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = service.get(key);
    }

    @Test
    public void testCreateNewProductNoResources() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = new Product(key,
                "testProduct", "testProduct");
        service.create(product);
    }

    @Test(expected = BadRequestException.class)
    public void testCreateNewProductsWithSameKey() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product1 = new Product(key,
                "testProduct1", "testProduct1");
        service.create(product1);
        Product product2 = new Product(key,
                "testProduct2", "testProduct2");
        service.create(product2);
    }

    @Test
    public void testUpdateProductSuccessful() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = new Product(key,
                "testProduct", "testProduct");
        service.create(product);

        Product toUpdate = new Product(key,
                "testProduct-updated", "testProduct-updated");
        service.update(toUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateProductNotFound() {
        String key = "test-product-" + System.currentTimeMillis();
        Product toUpdate = new Product(key,
                "testProduct-updated", "testProduct-updated");
        service.update(toUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateProductWithKeyChanged() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = new Product(key,
                "testProduct", "testProduct");
        service.create(product);

        Product toUpdate = new Product(key + "-updated",
                "testProduct-updated", "testProduct-updated");
        service.update(toUpdate);
    }

    @Test
    public void testCountProducts() {
        String count = service.count();
        Assert.assertNotNull(count);
    }

    @Test
    public void testListProducts() {
        List<Product> products = service.list();
        Assert.assertNotNull(products);
    }

    @Test
    public void testDeleteProductSuccessful() {
        String key = "test-product-" + System.currentTimeMillis();
        Product product = new Product(key,
                "testProduct", "testProduct");
        service.create(product);
        service.delete(key);
    }

    @Test(expected = BadRequestException.class)
    public void testDeleteProductNotFound() {
        String key = "test-product-" + System.currentTimeMillis();
        service.delete(key);
    }

}
