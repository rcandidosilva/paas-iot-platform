package platform.client;

import java.util.Random;
import org.apache.log4j.Logger;
import platform.api.Property;
import platform.client.delegate.PropertyServiceClient;

/**
 *
 * @author rodrigo
 */
public class PropertyRandomProcessor implements Runnable {

    private static final Logger logger = Logger.getLogger(PropertyRandomProcessor.class);

    private PropertyServiceClient service;

    private String productKey;
    private String deviceKey;
    private String propertyKey;

    private double start;
    private double end;
    private long delay;

    public PropertyRandomProcessor(String baseUri, String productKey,
            String deviceKey, String propertyKey, double start, double end,
            long delay) {
        this.productKey = productKey;
        this.deviceKey = deviceKey;
        this.propertyKey = propertyKey;
        this.start = start;
        this.end = end;
        this.delay = delay;
        this.service = new PropertyServiceClient(baseUri, productKey, deviceKey);
        logger.debug("Property processor created for product product '"
                + productKey + "', device '" + deviceKey + "', property '"
                + propertyKey + "'");
    }

    @Override
    public void run() {
        while (true) {
            Property property = service.get(propertyKey);
            double random = start + (end - start) * new Random().nextDouble();
            property.setValue(String.valueOf(random));
            service.update(property);
            logger.debug("Property updated for product '"
                    + productKey + "', device '" + deviceKey + "', property '"
                    + propertyKey + "' setting value: " + random);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                logger.error("Error processing the Thread.sleep().", ex);
            }
        }
    }

}
