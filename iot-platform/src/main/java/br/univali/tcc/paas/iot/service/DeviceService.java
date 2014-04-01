/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.tcc.paas.iot.service;

import br.univali.tcc.paas.iot.model.platform.Device;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/device")
public class DeviceService {

    @POST
    @Consumes("text/json")
    public void create(Device device) {
        
    }
    
    
    public void update() {
        
    }
    
    public Device get() {
        return null;
    } 
    
    public void delete() {
        
    }
    
    public List<Device> list() {
        return null;
    }
    
}
