package api;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rodrigo
 */
public interface Location extends Serializable {
    
    public double getLatitude();

    public void setLatitude(double latitude);

    public double getLongitude();

    public void setLongitude(double longitude);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);
    
}