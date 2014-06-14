package platform.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import platform.model.Widget;

/**
 *
 * @author rodrigo
 */
@Stateless
public class WidgetService {
    
    @PersistenceContext
    private EntityManager manager;
    
    public void save(Widget widget) {
        manager.persist(widget);
    }
    
    public void update(Widget widget) {
        manager.merge(widget);
    }
    
    public void delete(String id) {
        manager.remove(load(id));
    }
    
    public Widget load(String id) {
        return manager.find(Widget.class, id);
    }
    
    public List<Widget> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(Widget.class));
        return manager.createQuery(query).getResultList();
    }
}
