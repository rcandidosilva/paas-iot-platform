package platform.web;

import platform.web.ide.IDEController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import platform.model.Application;
import platform.service.ApplicationService;

/**
 *
 * @author rodrigo
 */
@Controller
@Scope("session")
public class ApplicationController implements Serializable {

    private static final Logger logger = Logger.getLogger(Application.class);

    private Application application;

    @Inject
    private ApplicationService service;

    @Inject
    private IDEController ideController;
    
    @PostConstruct
    public void init() {
        application = new Application();
        ideController.init();
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Application> getApplications() {
        // Force reset
        init();
        return service.list();
    }

    public String preview(Application application) {
        this.application = service.load(application.getId());
        ideController.edit(application);
        return ideController.preview();
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

    public void update(Application application) {
        service.update(application);
    }

}
