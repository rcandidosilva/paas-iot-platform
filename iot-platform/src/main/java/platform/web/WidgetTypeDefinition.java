package platform.web;

/**
 *
 * @author rodrigo
 */
public enum WidgetTypeDefinition {

    AREA(Constants.AREA_TYPE, Constants.AREA_CONTROLLER),
    BAR(Constants.BAR_TYPE, Constants.BAR_CONTROLLER),
    LINE(Constants.LINE_TYPE, Constants.LINE_CONTROLLER),
    LOCATION(Constants.LOCATION_TYPE, Constants.LOCATION_CONTROLLER),
    METER(Constants.METER_TYPE, Constants.METER_CONTROLLER),
    OHLC(Constants.OHLC_TYPE, Constants.OHLC_CONTROLLER),
    PIE(Constants.PIE_TYPE, Constants.PIE_CONTROLLER);

    private static class Constants {
        
        static final String AREA_TYPE = "area";
        static final String AREA_CONTROLLER = "areaWidgetController";

        static final String BAR_TYPE = "bar";
        static final String BAR_CONTROLLER = "barWidgetController";

        static final String LINE_TYPE = "line";
        static final String LINE_CONTROLLER = "lineWidgetController";

        static final String LOCATION_TYPE = "location";
        static final String LOCATION_CONTROLLER = "locationWidgetController";

        static final String METER_TYPE = "meter";
        static final String METER_CONTROLLER = "meterWidgetController";

        static final String OHLC_TYPE = "ohlc";
        static final String OHLC_CONTROLLER = "ohlcWidgetController";

        static final String PIE_TYPE = "pie";
        static final String PIE_CONTROLLER = "pieWidgetController";
    }

    public String type;
    public String controller;

    private WidgetTypeDefinition(String type, String controller) {
        this.type = type;
        this.controller = controller;
    }

}
