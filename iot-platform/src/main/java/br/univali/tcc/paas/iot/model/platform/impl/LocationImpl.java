/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.tcc.paas.iot.model.platform.impl;

import br.univali.tcc.paas.iot.model.platform.Location;
import java.util.Date;

/**
 *
 * @author rodrigo
 */
public class LocationImpl implements Location {
    
    private double latitude;
    private double longitude;
    private Date timestamp;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }    
    
}
