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
public interface Product extends Serializable {

    public int getId();

    public void setId(int id);

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