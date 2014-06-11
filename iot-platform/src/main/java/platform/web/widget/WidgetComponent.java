package platform.web.widget;

import java.io.Serializable;
import org.primefaces.component.panel.Panel;

/**
 *
 * @author rodrigo
 */
public interface WidgetComponent extends Serializable {
 
    public Panel create(String widgetId);
    
}
