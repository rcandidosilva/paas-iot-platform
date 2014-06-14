package platform.model;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;
import platform.api.Property;

/**
 *
 * @author rodrigo
 */
@Entity
@DiscriminatorValue(WidgetType.METER)
@NoSql(dataFormat = DataFormatType.MAPPED)
public class MeterWidget extends Widget {

    private String label;
    @ElementCollection
    private List<Double> intervals;
    @ManyToOne
    private Property property;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Double> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Double> intervals) {
        this.intervals = intervals;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

}
