package platform.web.widget;

import java.io.Serializable;
import platform.model.Widget;

/**
 *
 * @author rodrigo
 */
public interface WidgetController extends Serializable {

    public void init();
    
    public void setWidget(Widget widget);
    
    public Widget getWidget();
    
}
