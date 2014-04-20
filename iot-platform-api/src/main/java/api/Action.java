package api;

import java.io.Serializable;

/**
 *
 * @author rodrigo
 */
public interface Action extends Serializable {
   
    public String getName();
    
    public void setName(String name);
    
}
