package platform.service.test;

import platform.service.test.client.DeviceServiceClient;
import java.util.List;
import javax.ws.rs.BadRequestException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import platform.api.Device;
import platform.api.Location;

/**
 *
 * @author Rodrigo Cândido da Silva
 */
public class DeviceServiceTest {

    private static final String SERVICE_URL
            = "http://127.0.0.1:8080/iot-platform/rest/";

    private static DeviceServiceClient service;

    @BeforeClass
    public static void start() {
        service = new DeviceServiceClient(SERVICE_URL);
    }

    @AfterClass
    public static void end() {
        service.close();
    }

    @Test
    public void testGetDeviceSuccessful() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        service.create(device);
        Device loaded = service.get(key);
        Assert.assertNotNull(loaded);
        Assert.assertNotNull(loaded.getKey());
        Assert.assertTrue(loaded.getKey().equals(key));
        Assert.assertNotNull(loaded.getName());
        Assert.assertTrue(loaded.getName().equals("testDevice"));
        Assert.assertNotNull(loaded.getDescription());
        Assert.assertTrue(loaded.getDescription().equals("testDevice"));
    }

    @Test(expected = BadRequestException.class)
    public void testGetDeviceNotFound() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = service.get(key);
    }

    @Test
    public void testCreateNewDeviceNoResources() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        service.create(device);
    }

    @Test(expected = BadRequestException.class)
    public void testCreateNewDevicesWithSameKey() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device1 = new Device(key,
                "testDevice1", "testDevice1", null);
        service.create(device1);
        Device device2 = new Device(key,
                "testDevice2", "testDevice2", null);
        service.create(device2);
    }

    @Test
    public void testUpdateDeviceSuccessful() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        service.create(device);

        Device toUpdate = new Device(key,
                "testDevice-updated", "testDevice-updated", new Location(3.0, 4.0));
        service.update(toUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateDeviceNotFound() {
        String key = "test-device-" + System.currentTimeMillis();
        Device toUpdate = new Device(key,
                "testDevice-updated", "testDevice-updated", new Location(3.0, 4.0));
        service.update(toUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateDeviceWithKeyChanged() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        service.create(device);

        Device toUpdate = new Device(key + "-updated",
                "testDevice-updated", "testDevice-updated", new Location(3.0, 4.0));
        service.update(toUpdate);
    }

    @Test
    public void testCountDevices() {
        String count = service.count();
        Assert.assertNotNull(count);
    }

    @Test
    public void testListDevices() {
        List<Device> devices = service.list();
        Assert.assertNotNull(devices);
    }

    @Test
    public void testDeleteDeviceSuccessful() {
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        service.create(device);
        service.delete(key);
    }

    @Test(expected = BadRequestException.class)
    public void testDeleteDeviceNotFound() {
        String key = "test-device-" + System.currentTimeMillis();
        service.delete(key);
    }

}
