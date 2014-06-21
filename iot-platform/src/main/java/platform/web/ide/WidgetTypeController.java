package platform.web.ide;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
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
    
    private List<String> types;
    
    @Inject
    private WidgetTypeService service;

    @PostConstruct
    public void init() {
        types = Arrays.asList(new String[] {
            WidgetType.AREA, WidgetType.BAR, WidgetType.LINE,
            WidgetType.LOCATION, WidgetType.METER, WidgetType.OHLC,
            WidgetType.PIE
        });
        widgetType = new WidgetType();
    }

    public WidgetType getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(WidgetType widgetType) {
        this.widgetType = widgetType;
    }

    public List<String> getTypes() {
        return types;
    }
    
    public List<WidgetType> getWidgetTypeList() {
        return service.list();
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

    public String save() {
        service.save(widgetType);
        return "/pages/widgetType/list";
    }

    public String createNew() {
        widgetType = new WidgetType();
        return "/pages/widgetType/edit";
    }

}
