package platform.web.ide.widget;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import platform.model.Widget;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
@Controller
@Scope("session")
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
