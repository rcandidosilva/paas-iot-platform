package platform.api;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Represents a device property for a smart device
 *
 * @author Rodrigo Cândido da Silva
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class Property implements Serializable {
    
    @Id @GeneratedValue
    @Field(name = "_id")
    private String id;
    private String key;
    private String name;
    private String value;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne
    private Device device;

    public Property() {
        super();
    }

    public Property(String key, String value, String description, 
            Date timestamp) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.timestamp = timestamp;
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
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return key;
    }
            
}