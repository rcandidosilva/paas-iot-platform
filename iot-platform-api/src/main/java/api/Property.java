package api;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rodrigo
 */
public interface Property extends Serializable {
    
    public String getKey();

    public void setKey(String key);

    public String getValue();

    public void setValue(String value);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);    
    
}
