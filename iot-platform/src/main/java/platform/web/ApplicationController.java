package platform.web;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import platform.model.Application;
import platform.service.ApplicationService;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class ApplicationController implements Serializable {
    
    private static final Logger logger = Logger.getLogger(Application.class);

    private Application application = new Application();    
    
    @Inject
    private ApplicationService service;
    
    @Inject
    private IDEController ideController;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    public List<Application> getApplications() {
        return service.list();
    }
    
    public String edit(Application application) {
        this.application = service.load(application.getId());
        return ideController.edit(application);
    }
    
    public void delete(Application application) {
        service.delete(application.getId());
    }
    
    public String newApplication() {
        application = new Application();
        ideController.init();
        return "/pages/ide/index";
    }
    
    public String newWorkflow() {
        return "/pages/workflow/index";
    }
    
    public void save(Application application) {
        service.save(application);
    }
    
   
}
