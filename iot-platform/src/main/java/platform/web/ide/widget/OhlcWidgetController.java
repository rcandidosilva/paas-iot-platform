package platform.web.ide.widget;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import platform.model.Widget;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
@Named
@ViewScoped
public class OhlcWidgetController implements WidgetController {

    private Widget widget;

    @PostConstruct
    @Override
    public void init() {
        widget = new Widget(WidgetType.OHLC);
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public void addAction() {
        // TODO
    }

}
