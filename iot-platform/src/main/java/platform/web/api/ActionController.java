package platform.web.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Action;
import platform.api.ActionParameter;
import platform.api.Device;
import platform.api.Product;
import platform.service.api.ActionService;
import platform.service.api.DeviceService;
import platform.service.api.ProductDeviceService;
import platform.service.api.ProductService;
import platform.web.JSFHelper;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class ActionController implements Serializable {

    private Action action = new Action();
    private ActionParameter actionParameter = new ActionParameter();
    private String selectedDeviceKey;
    private String selectedProductKey;

    private ListDataModel<ActionParameter> parametersModel;

    @Inject
    private ActionService actionService;

    @Inject
    private DeviceService deviceService;

    @Inject
    private ProductService productService;

    @Inject
    private ProductDeviceService productDeviceService;

    @PostConstruct
    public void init() {
        action = new Action();
        actionParameter = new ActionParameter();
        parametersModel = new ListDataModel<>(
                new ArrayList<ActionParameter>());
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ActionParameter getActionParameter() {
        return actionParameter;
    }

    public void setActionParameter(ActionParameter actionParameter) {
        this.actionParameter = actionParameter;
    }

    public List<Action> getActionList() {
        return actionService.listAll();
    }

    public List<Device> getDeviceList() {
        if (selectedProductKey != null && !"".equals(selectedProductKey)) {
            return productDeviceService.list(selectedProductKey);
        } else {
            return deviceService.list();
        }
    }

    public List<Product> getProductList() {
        return productService.list();
    }

    public String getSelectedDeviceKey() {
        return selectedDeviceKey;
    }

    public void setSelectedDeviceKey(String selectedDeviceKey) {
        this.selectedDeviceKey = selectedDeviceKey;
    }

    public String getSelectedProductKey() {
        return selectedProductKey;
    }

    public void setSelectedProductKey(String selectedProductKey) {
        this.selectedProductKey = selectedProductKey;
    }

    public ListDataModel<ActionParameter> getParametersModel() {
        return parametersModel;
    }

    public void setParametersModel(ListDataModel<ActionParameter> parametersModel) {
        this.parametersModel = parametersModel;
    }
    
    public void newParameter() {
        ((List) parametersModel.getWrappedData()).add(actionParameter);
        actionParameter = new ActionParameter();
    }

    public void deleteParameter() {
        ((List) parametersModel.getWrappedData()).remove(
                parametersModel.getRowData());
    }    

    public void delete(Action action) {
        if (action.getDevice() != null) {
            actionService.delete(action.getDevice().getKey(), action.getName());
        } else {
            actionService.delete(action.getName());
        }
    }

    public String edit(Action action) {
        this.action = actionService.get(action.getDevice().getKey(), action.getName());
        this.selectedDeviceKey = action.getDevice().getKey();
        this.parametersModel = new ListDataModel<>(action.getParameters());
        return "edit";
    }

    public String save() {
        List<ActionParameter> parameters = (List<ActionParameter>) 
                parametersModel.getWrappedData();
        action.setParameters(parameters);
        actionService.create(selectedDeviceKey, action);
        JSFHelper.addSuccessMessage("Action saved successfuly.");
        return "/pages/action/list";
    }

    public String createNew() {
        action = new Action();
        selectedDeviceKey = null;
        parametersModel = new ListDataModel<>(
                new ArrayList<ActionParameter>());
        return "/pages/action/edit";
    }

}
