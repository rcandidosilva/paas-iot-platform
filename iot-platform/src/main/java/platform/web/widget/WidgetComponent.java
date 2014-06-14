package platform.web.widget;

import java.io.Serializable;
import platform.model.Widget;

/**
 *
 * @author rodrigo
 */
public interface WidgetComponent extends Serializable {
 
    public String getTitle();
    
    public Object createComponent(String widgetId);
    
    public void update();
    
    public String getType();
    
    public Widget getWidget();
    
    public void setWidgetId(String widgetId);
    
    public String getWidgetId();
    
    public void setService(Object service);
    
}
