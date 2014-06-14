package platform.web.dashboard;

import java.io.Serializable;
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
public class OhlcWidgetController implements Serializable {

    private Widget widget;

    @PostConstruct
    public void init() {
        widget = new Widget(WidgetType.OHLC);
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public void addAction() {
        // TODO
    }

}
