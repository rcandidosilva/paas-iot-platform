package platform.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import platform.model.Application;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class ApplicationController implements Serializable {

    private Application application = new Application();    

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    public List<Application> getApplications() {
        return new ArrayList<>();
    }
    
}
