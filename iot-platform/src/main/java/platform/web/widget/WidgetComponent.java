package platform.web.widget;

import java.io.Serializable;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
public interface WidgetComponent extends Serializable {
 
    public String getTitle();
    
    public Object create(String widgetId);
    
    public void update();
    
    public String getType();
    
}
