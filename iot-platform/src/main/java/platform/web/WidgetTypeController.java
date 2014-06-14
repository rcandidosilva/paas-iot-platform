package platform.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import platform.model.Widget;
import platform.model.WidgetType;
import platform.service.WidgetTypeService;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class WidgetTypeController implements Serializable {

    private static final Logger logger = Logger.getLogger(WidgetTypeController.class);
    
    private WidgetType widgetType;
    private List<WidgetType> typeList;    
    
    @Inject
    private WidgetTypeService service;

    @PostConstruct
    public void init() {
        widgetType = new WidgetType();
        typeList = new ArrayList<>();
    }

    public WidgetType getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(WidgetType widgetType) {
        this.widgetType = widgetType;
    }

    public List<WidgetType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<WidgetType> typeList) {
        this.typeList = typeList;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        widgetType.setIconContentType(file.getContentType());
        byte[] contents = IOUtils.toByteArray(file.getInputstream());
        widgetType.setIconFile(contents);
    }

    public StreamedContent getIconFile() {
        byte[] bytes = widgetType.getIconFile();
        return new DefaultStreamedContent(
                new ByteArrayInputStream(bytes), widgetType.getIconContentType());
    }
    
    public void removeIconFile() {
        widgetType.setIconContentType(null);
        widgetType.setIconFile(null);
    }

    public void delete(WidgetType type) {
        service.delete(type.getId());
    }

    public String edit(WidgetType type) {
        this.widgetType = service.load(type.getId());
        return "/pages/widgetType/edit";
    }

    public void save() {
        service.save(widgetType);
    }

    public String createNew() {
        widgetType = new WidgetType();
        return "/pages/widgetType/edit";
    }

}
