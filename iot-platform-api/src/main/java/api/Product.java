package api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public interface Product extends Serializable {

    public String getId();

    public void setId(String id);

    public Date getCreatedAt();

    public void setCreatedAt(Date createdAt);

    public Date getUpdatedAt();

    public void setUpdatedAt(Date updatedAt);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public String getBrand();

    public void setBrand(String brand);

    public List<Device> getDevices();

    public void setDevices(List<Device> devices);
    
}