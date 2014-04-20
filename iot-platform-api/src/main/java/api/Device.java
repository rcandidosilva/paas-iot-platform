/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public interface Device extends Serializable {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Date getCreatedAt();

    public void setCreatedAt(Date createdAt);

    public Date getUpdatedAt();

    public void setUpdatedAt(Date updatedAt);

    public Location getLocation();

    public void setLocation(Location location);

    public List<? extends Property> getProperties();

    public void setProperties(List properties);
        
}
