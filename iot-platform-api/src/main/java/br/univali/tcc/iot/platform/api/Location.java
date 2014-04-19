/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.tcc.paas.iot.model.platform;

import java.util.Date;

/**
 *
 * @author rodrigo
 */
public interface Location {
    
    public double getLatitude();

    public void setLatitude(double latitude);

    public double getLongitude();

    public void setLongitude(double longitude);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);
    
}