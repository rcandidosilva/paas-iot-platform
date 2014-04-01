/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.tcc.paas.iot.model.platform.impl;

import br.univali.tcc.paas.iot.model.platform.Property;
import java.util.Date;

/**
 *
 * @author rodrigo
 */
public class PropertyImpl implements Property {
    
    private String key;
    private String value;
    private Date timestamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
}
