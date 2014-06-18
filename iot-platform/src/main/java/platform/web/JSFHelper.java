package platform.web;

import java.io.IOException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rodrigo
 */
public class JSFHelper {

    public static void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }

    public static void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
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

    public static String getBaseUrl() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(request.getScheme()).append("://").append(request.getServerName()).append(
                ":").append(request.getServerPort()).append(request.getContextPath());
        return baseUrl.toString();
    }
    
    public static void sendRedirect(String url) throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(url);
    }

}
