package platform.service.test;

import java.util.Date;
import java.util.List;
import javax.ws.rs.BadRequestException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import platform.api.Device;
import platform.api.Location;
import platform.api.Property;
import platform.service.test.client.DeviceServiceClient;
import platform.service.test.client.PropertyServiceClient;

/**
 *
 * Unit test class for the REST PropertyService
 *
 * @author Rodrigo Cândido da Silva
 */
public class PropertyServiceTest {

    private static final String SERVICE_URL
            = "http://127.0.0.1:8081/iot-platform/rest/";

    private static PropertyServiceClient propertyService;
    private static DeviceServiceClient deviceService;

    @BeforeClass
    public static void start() {
        deviceService = new DeviceServiceClient(SERVICE_URL);
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        deviceService.create(device);
        propertyService = new PropertyServiceClient(SERVICE_URL, key);
    }

    @AfterClass
    public static void end() {
        propertyService.close();
        deviceService.close();
    }

    @Test
    public void testCreateNewPropertySuccessful() {
        String key = "test-property-" + System.currentTimeMillis();
        Property property = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty",
                new Date());
        propertyService.create(property);
    }

    @Test(expected = BadRequestException.class)
    public void testCreatePropertyAlreadyExists() {
        String key = "test-property-" + System.currentTimeMillis();
        Property property1 = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty1",
                new Date());
        propertyService.create(property1);
        Property property2 = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty2",
                new Date());
        propertyService.create(property2);
    }

    @Test
    public void testUpdatePropertySuccessful() {
        String key = "test-property-" + System.currentTimeMillis();
        Property property = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty",
                new Date());
        propertyService.create(property);

        Property toUpdate = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty",
                new Date());
        propertyService.update(toUpdate);
    }

    @Test
    public void testDeletePropertySuccessful() {
        String key = "test-property-" + System.currentTimeMillis();
        Property property = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty",
                new Date());
        propertyService.create(property);

        Property toDelete = new Property(key,
                String.valueOf(System.currentTimeMillis()), "testProperty",
                new Date());
        propertyService.delete(key);
    }

    @Test
    public void testCountProperties() {
        String count = propertyService.count();
        Assert.assertNotNull(count);
    }

    @Test
    public void testListProperties() {
        List<Property> properties = propertyService.list();
        Assert.assertNotNull(properties);
    }

}
