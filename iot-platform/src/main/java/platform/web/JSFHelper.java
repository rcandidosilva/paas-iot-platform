/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package platform.web;

import javax.faces.application.FacesMessage;
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
    
}
