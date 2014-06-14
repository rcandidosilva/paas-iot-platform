package platform.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;
import platform.api.Property;

/**
 *
 * @author rodrigo
 */
@Entity
@DiscriminatorValue(WidgetType.LINE)
@NoSql(dataFormat = DataFormatType.MAPPED)
public class LineWidget extends Widget {

    private Integer interval;
    @ManyToOne
    private Property property;

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

}
