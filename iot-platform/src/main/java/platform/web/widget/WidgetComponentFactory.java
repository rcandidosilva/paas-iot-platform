package platform.web.widget;

import java.io.Serializable;
import javax.inject.Inject;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.PropertyService;

/**
 *
 * @author rodrigo
 */
public class WidgetComponentFactory implements Serializable {

    @Inject
    private PropertyService propertyService;

    public WidgetComponent createComponent(Widget widget) {
        return createComponent(widget, null);
    }
    
    public WidgetComponent createComponent(Widget widget, String widgetId) {
        WidgetComponent component = null;
        String type = widget.getType();
        if (WidgetType.AREA.equals(type)) {
            component = new UIAreaComponent(widget, propertyService);
        } else if (WidgetType.BAR.equals(type)) {
            component = new UIBarComponent(widget, propertyService);
        } else if (WidgetType.LINE.equals(type)) {
            component = new UILineComponent(widget, propertyService);
        } else if (WidgetType.LOCATION.equals(type)) {
            component = new UILocationComponent(widget);
        } else if (WidgetType.METER.equals(type)) {
            component = new UIMeterComponent(widget, propertyService);
        } else if (WidgetType.OHLC.equals(type)) {
            component = new UIOhlcComponent(widget);
        } else if (WidgetType.PIE.equals(type)) {
            component = new UIPieComponent(widget);
        }
        if (widgetId != null) {
            component.setWidgetId(widgetId);
        }
        return component;
    }

}
