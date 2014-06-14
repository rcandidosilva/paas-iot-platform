package platform.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import platform.model.Application;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ApplicationService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private WidgetService widgetService;
    
    public void save(Application application) {
        manager.persist(application);
    }

    public void update(Application application) {
        manager.merge(application);
    }

    public void delete(String id) {
        manager.remove(load(id));
    }

    public Application load(String id) {
        return manager.find(Application.class, id);
    }

    public List<Application> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Application.class));
        return manager.createQuery(query).getResultList();
    }
}
