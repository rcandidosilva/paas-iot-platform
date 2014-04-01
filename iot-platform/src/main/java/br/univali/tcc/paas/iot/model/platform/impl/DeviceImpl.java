/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.tcc.paas.iot.model.platform.impl;

import br.univali.tcc.paas.iot.model.platform.Device;
import br.univali.tcc.paas.iot.model.platform.Location;
import br.univali.tcc.paas.iot.model.platform.Property;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class DeviceImpl implements Device {
    
    private int id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private LocationImpl location;
    private List<PropertyImpl> properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocationImpl getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = (LocationImpl) location;
    }

    public List<PropertyImpl> getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
    }
    
    
    
}
