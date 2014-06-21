package platform.web.ide.widget.ui;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import platform.model.Widget;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
@Named
@Dependent
public class UIPieComponent implements WidgetComponent {

    private String widgetId;

    private String title;

    private Widget widget;

    public UIPieComponent(Widget widget) {
        this.widget = widget;
    }

    @Override
    public Object createComponent(String widgetId) {
        this.widgetId = widgetId;

        // TODO
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public String getType() {
        return WidgetType.PIE;
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setService(Object service) {
        // TODO
    }

    @Override
    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public String getWidgetId() {
        return widgetId;
    }

}
