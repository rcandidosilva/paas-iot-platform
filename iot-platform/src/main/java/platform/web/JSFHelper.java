package platform.web;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
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

    public static Behavior createBehavior(String behaviorId) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().createBehavior(behaviorId);
    }

    public static MethodExpression createMethodExpression(String el,
            Class returnType, Class[] paramTypes) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpressionFactory factory = ExpressionFactory.newInstance();
        return factory.createMethodExpression(
                context.getELContext(),
                el, null, new Class[]{String.class});
    }

}
