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
public interface Property {
    
    public String getKey();

    public void setKey(String key);

    public String getValue();

    public void setValue(String value);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);    
    
}
