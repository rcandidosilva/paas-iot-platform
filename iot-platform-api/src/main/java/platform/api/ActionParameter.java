package platform.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * Represents an action parameter for a smart device
 *
 * @author Rodrigo Cândido da Silva
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class ActionParameter {

    @Id
    @GeneratedValue
    @Field(name = "_id")
    private String id;
    private String name;
    private String description;
    private String value;
    private String type;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}