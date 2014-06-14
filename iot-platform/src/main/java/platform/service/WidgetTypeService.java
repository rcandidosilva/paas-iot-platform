package platform.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import platform.model.WidgetType;

/**
 *
 * @author rodrigo
 */
@Stateless
public class WidgetTypeService {
    
    @PersistenceContext
    private EntityManager manager;
    
    public void save(WidgetType type) {
        type.setCreatedAt(new Date());
        manager.persist(type);
    }
    
    public void update(WidgetType type) {
        type.setUpdatedAt(new Date());
        manager.merge(type);
    }
    
    public void delete(String id) {
        manager.remove(load(id));
    }
    
    public WidgetType load(String id) {
        return manager.find(WidgetType.class, id);
    }
    
    public WidgetType loadByType(String type) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<WidgetType> query = builder.createQuery(WidgetType.class);
        Root<WidgetType> root = query.from(WidgetType.class);
        query.select(root);
        query.where(builder.equal(root.get("type"), type));
        List<WidgetType> list = manager.createQuery(query).getResultList();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public List<WidgetType> list() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery();
        query.select(query.from(WidgetType.class));
        return manager.createQuery(query).getResultList();
    }
}
