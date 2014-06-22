package platform.client;

/**
 *
 * @author rodrigo
 */
public class Main {

    private static final String SERVICE_URL
            = "http://ec2-54-88-30-52.compute-1.amazonaws.com/iot-platform/rest/";

    public static void main(String[] args) {
        // /product/neocut/device/knife/property/vibration-speed
//        PropertyRandomProcessor vibrationSpeed
//                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
//                        "knife", "vibration-speed", 0, 9, 3000, false);
//        new Thread(vibrationSpeed).start();
        // /product/neocut/device/knife/property/cut-angle
//        PropertyRandomProcessor cutAngle
//                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
//                        "knife", "cut-angle", 0, 180, 5000, true);
//        new Thread(cutAngle).start();
        // /product/neocut/device/cutting-head/property/head-speed
//        PropertyRandomProcessor headSpeed
//                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
//                        "cutting-head", "head-speed", 0, 80, 5000, false);
//        new Thread(headSpeed).start();
        // /product/neocut/device/turbine/property/frequency
//        PropertyRandomProcessor frequency
//                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
//                        "turbine", "frequency", 0, 50, 5000, false);
//        new Thread(frequency).start();

        // /product/neocut/device/execution/property/status
        PropertyRandomProcessor executionStatus
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "execution", "status", 0, 1, 10000, true);
        new Thread(executionStatus).start();
        
        // /product/neocut/device/cutting-head/property/head-speed-percentage
//        PropertyRandomProcessor headSpeedPercentage
//                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
//                        "cutting-head", "head-speed-percentage", 1, 100, 5000, false);
//        new Thread(headSpeedPercentage).start();
        
        // /product/neocut/device/cutting-head/property/head-speed-percentage
        PropertyRandomProcessor moveVariance
                = new PropertyRandomProcessor(SERVICE_URL, "neocut",
                        "cutting-conveyor", "move-variance", 0, 10000, 5000, true);
        new Thread(moveVariance).start();        
    }

}
