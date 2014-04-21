package platform.api.impl;

import api.Action;

/**
 *
 * @author rodrigo
 */
public class ActionImpl implements Action {
    
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
}