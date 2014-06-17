package platform.client;

/**
 *
 * @author rodrigo
 */
public class Main {

    private static final String SERVICE_URL
            = "http://127.0.0.1:8080/iot-platform/rest/";

    public static void main(String[] args) {
        // /product/neocut/device/knife/property/vibration-speed
        PropertyRandomProcessor vibrationSpeed
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "knife", "vibration-speed", 0, 100, 5000);
        new Thread(vibrationSpeed).start();
        // /product/neocut/device/knife/property/cut-angle
        PropertyRandomProcessor cutAngle
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "knife", "cut-angle", 0, 180, 5000);
        new Thread(cutAngle).start();
        // /product/neocut/device/cutting-head/property/head-speed
        PropertyRandomProcessor headSpeed
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "cutting-head", "head-speed", 0, 80, 5000);
        new Thread(headSpeed).start();
        // /product/neocut/device/turbine/property/frequency
        PropertyRandomProcessor frequency
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "turbine", "frequency", 0, 50, 5000);
        new Thread(frequency).start();

    }

}
