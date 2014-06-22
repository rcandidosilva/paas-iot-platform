package platform.web.ide;

import java.io.Serializable;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import org.springframework.stereotype.Component;
import platform.model.Widget;
import platform.service.api.PropertyService;
import static platform.web.ide.WidgetTypeDefinition.AREA;
import static platform.web.ide.WidgetTypeDefinition.BAR;
import static platform.web.ide.WidgetTypeDefinition.LINE;
import static platform.web.ide.WidgetTypeDefinition.LOCATION;
import static platform.web.ide.WidgetTypeDefinition.METER;
import static platform.web.ide.WidgetTypeDefinition.OHLC;
import static platform.web.ide.WidgetTypeDefinition.PIE;
import platform.web.ide.widget.WidgetController;
import platform.web.ide.widget.ui.UIAreaComponent;
import platform.web.ide.widget.ui.UIBarComponent;
import platform.web.ide.widget.ui.UILineComponent;
import platform.web.ide.widget.ui.UILocationComponent;
import platform.web.ide.widget.ui.UIMeterComponent;
import platform.web.ide.widget.ui.UIOhlcComponent;
import platform.web.ide.widget.ui.UIPieComponent;
import platform.web.ide.widget.ui.WidgetComponent;

/**
 *
 * @author rodrigo
 */
@Component
public class WidgetFactory implements Serializable {

    @Inject
    private PropertyService propertyService;

    public WidgetComponent createComponent(Widget widget) {
        return createComponent(widget, null);
    }

    public WidgetComponent createComponent(Widget widget, String widgetId) {
        WidgetComponent component = null;
        String type = widget.getType();
        if (AREA.type.equals(type)) {
            component = new UIAreaComponent(widget, propertyService);
        } else if (BAR.type.equals(type)) {
            component = new UIBarComponent(widget, propertyService);
        } else if (LINE.type.equals(type)) {
            component = new UILineComponent(widget, propertyService);
        } else if (LOCATION.type.equals(type)) {
            component = new UILocationComponent(widget);
        } else if (METER.type.equals(type)) {
            component = new UIMeterComponent(widget, propertyService);
        } else if (OHLC.type.equals(type)) {
            component = new UIOhlcComponent(widget);
        } else if (PIE.type.equals(type)) {
            component = new UIPieComponent(widget);
        }
        if (widgetId != null) {
            component.setWidgetId(widgetId);
        }
        return component;
    }

    public WidgetController getController(Widget widget) {
        Object controller = null;
        String type = widget.getType();
        if (AREA.type.equals(type)) {
            controller = getBean(AREA.controller);
        } else if (BAR.type.equals(type)) {
            controller = getBean(BAR.controller);
        } else if (LINE.type.equals(type)) {
            controller = getBean(LINE.controller);
        } else if (LOCATION.type.equals(type)) {
            controller = getBean(LOCATION.controller);
        } else if (METER.type.equals(type)) {
            controller = getBean(METER.controller);
        } else if (OHLC.type.equals(type)) {
            controller = getBean(OHLC.controller);
        } else if (PIE.type.equals(type)) {
            controller = getBean(PIE.controller);
        }
        return (WidgetController) controller;
    }

    private Object getBean(String name) {
        BeanManager manager = CDI.current().getBeanManager();
        Bean bean = manager.getBeans(name).iterator().next();
        return manager.getReference(bean, bean.getBeanClass(), 
                manager.createCreationalContext(bean));
    }

}
