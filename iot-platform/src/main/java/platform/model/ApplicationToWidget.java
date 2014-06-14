package platform.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author rodrigo
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class ApplicationToWidget implements Serializable {

    @Id
    @GeneratedValue
    @Field(name = "_id")
    private String id;
    private String widgetId;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Application application;
    @ManyToOne(cascade = CascadeType.ALL)
    private Widget widget;
    
    @Column(insertable = false, updatable = false, name = "WIDGET__id")
    private String relatedWidgetId;
    @Column(insertable = false, updatable = false, name = "APPLICATION__id")
    private String relatedApplicationId;

    public ApplicationToWidget() {
        super();
    }

    public ApplicationToWidget(String widgetId, Application application) {
        this.widgetId = widgetId;
        this.application = application;    
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public String getRelatedWidgetId() {
        return relatedWidgetId;
    }

    public void setRelatedWidgetId(String relatedWidgetId) {
        this.relatedWidgetId = relatedWidgetId;
    }

    public String getRelatedApplicationId() {
        return relatedApplicationId;
    }

    public void setRelatedApplicationId(String relatedApplicationId) {
        this.relatedApplicationId = relatedApplicationId;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.widgetId);
        if (application != null) {
            hash = 71 * hash + Objects.hashCode(this.application);
        } else if (relatedApplicationId != null) {
            hash = 71 * hash + Objects.hashCode(this.relatedApplicationId);
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApplicationToWidget other = (ApplicationToWidget) obj;
        if (!Objects.equals(this.widgetId, other.widgetId)) {
            return false;
        }
        if (this.application != null) {
            if (!Objects.equals(this.application, other.application)) {
                return false;
            }
        } else if (this.relatedApplicationId != null) {
            if (!Objects.equals(this.relatedApplicationId, 
                    other.relatedApplicationId)) {
                return false;
            }
        }
        return true;
    }
    
    

}
