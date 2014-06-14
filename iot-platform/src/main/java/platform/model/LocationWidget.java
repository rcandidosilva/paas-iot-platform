package platform.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author rodrigo
 */
@Entity
@DiscriminatorValue(WidgetType.LOCATION)
@NoSql(dataFormat = DataFormatType.MAPPED)
public class LocationWidget extends Widget {


}
