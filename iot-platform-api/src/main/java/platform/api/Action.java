package platform.api;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * 
 * Represents an action for a smart device
 *
 * @author Rodrigo Cândido da Silva
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class Action implements Serializable {
    
    @Id @GeneratedValue
    @Field(name = "_id")
    private String id;
    private String name;
    private String description;
    @ManyToOne
    private Device device;

    public Action() {
        super();
    }

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }    

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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    
}
