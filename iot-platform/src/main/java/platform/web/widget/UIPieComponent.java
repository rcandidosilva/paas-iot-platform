package platform.web.widget;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
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
    
    @Override
    public Object create(String widgetId) {
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
    
}
