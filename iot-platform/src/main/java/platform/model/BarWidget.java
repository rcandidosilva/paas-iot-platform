package platform.model;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;
import platform.api.Property;

/**
 *
 * @author rodrigo
 */
@Entity
@DiscriminatorValue(WidgetType.BAR)
@NoSql(dataFormat = DataFormatType.MAPPED)
public class BarWidget extends Widget {

    private Integer interval;
    @OneToMany
    private List<Property> properties;

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
