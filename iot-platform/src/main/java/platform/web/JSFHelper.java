package platform.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author rodrigo
 */
public class JSFHelper {
    
    public static void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(message));
    }
    
    public static UIComponent createComponent(String componentType) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().createComponent(componentType);
    }
    
}
