package platform.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author rodrigo
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class Application implements Serializable {
    
    @Id @GeneratedValue
    @Field(name = "_id")
    private String id;
    private String name;
    private String description;
    private boolean deployed;
    @OneToMany
    private List<ApplicationToWidget> widgets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }   

    public List<ApplicationToWidget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<ApplicationToWidget> widgets) {
        this.widgets = widgets;
    }
    
}
