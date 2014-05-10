package platform.service.test;

import java.util.List;
import javax.ws.rs.BadRequestException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import platform.api.Action;
import platform.api.Device;
import platform.api.Location;
import platform.service.test.client.ActionServiceClient;
import platform.service.test.client.DeviceServiceClient;

/**
 *
 * Unit test class for the REST ActionService
 *
 * @author Rodrigo Cândido da Silva
 */
public class ActionServiceTest {

    private static final String SERVICE_URL
            = "http://127.0.0.1:8081/iot-platform/rest/";

    private static ActionServiceClient actionService;
    private static DeviceServiceClient deviceService;

    @BeforeClass
    public static void start() {
        deviceService = new DeviceServiceClient(SERVICE_URL);
        String key = "test-device-" + System.currentTimeMillis();
        Device device = new Device(key,
                "testDevice", "testDevice", new Location(1.0, 2.0));
        deviceService.create(device);
        actionService = new ActionServiceClient(SERVICE_URL, key);
    }

    @AfterClass
    public static void end() {
        actionService.close();
        deviceService.close();
    }

    @Test
    public void testCreateNewActionSuccessful() {
        String name = "test-action-" + System.currentTimeMillis();
        Action action = new Action(name, name);
        actionService.create(action);
    }

    @Test(expected = BadRequestException.class)
    public void testCreateActionAlreadyExists() {
        String name = "test-action-" + System.currentTimeMillis();
        Action action1 = new Action(name, name);
        actionService.create(action1);
        Action action2 = new Action(name, name);
        actionService.create(action2);
    }

    @Test
    public void testUpdateActionSuccessful() {
        String name = "test-action-" + System.currentTimeMillis();
        Action action = new Action(name, name);
        actionService.create(action);

        Action toUpdate = new Action(name, name);
        actionService.update(toUpdate);
    }

    @Test
    public void testDeleteActionSuccessful() {
        String name = "test-action-" + System.currentTimeMillis();
        Action action = new Action(name, name);
        actionService.create(action);

        actionService.delete(name);
    }

    @Test
    public void testCountActions() {
        String count = actionService.count();
        Assert.assertNotNull(count);
    }

    @Test
    public void testListActions() {
        List<Action> actions = actionService.list();
        Assert.assertNotNull(actions);
    }

}
