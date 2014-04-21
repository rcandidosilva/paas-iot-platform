package platform.client.test;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import platform.api.Device;
import platform.api.Location;
import platform.client.DeviceServiceClient;

/**
 *
 * @author Rodrigo Cândido da Silva
 */
public class DeviceServiceTest {

    private static final String SERVICE_URL = 
            "http://localhost:8080/iot-platform/rest/";
    
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
    public void testCreateNewDeviceNoResources() {
        Location location = new Location(1.0, 2.0);
        Device device = new Device("testDevice", 
                "testDevice", "testDevice", location);
        service.create(device);
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
    public void testDeleteDevice() {
        List<Device> devices = service.list();
        Assert.assertNotNull(devices);
        if (!devices.isEmpty()) {
            service.delete(devices.get(0).getId());
        }
    }


}
