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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import platform.model.Widget;
import platform.service.WidgetService;

/**
 *
 * @author rodrigo
 */
@Named(value = "widgetMB")
@SessionScoped
public class WidgetMB implements Serializable {

    @Inject
    private WidgetService service;

    private Widget widget;
    private List<Widget> widgetLit;

    @PostConstruct
    public void init() {
        widget = new Widget();
        widgetLit = new ArrayList<>();
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public List<Widget> getWidgetLit() {
        return service.list();
    }

    public void setWidgetLit(List<Widget> widgetLit) {
        this.widgetLit = widgetLit;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        widget.setIconContentType(file.getContentType());
        byte[] contents = IOUtils.toByteArray(file.getInputstream());
        widget.setIconFile(contents);
    }

    public StreamedContent getIconFile() {
        byte[] bytes = widget.getIconFile();
        return new DefaultStreamedContent(
                new ByteArrayInputStream(bytes), widget.getIconContentType());
    }
    
    public void removeIconFile() {
        widget.setIconContentType(null);
        widget.setIconFile(null);
    }

    public void delete(Widget widget) {
        service.delete(widget.getId());
    }

    public String edit(Widget widget) {
        this.widget = service.load(widget.getId());
        return "edit";
    }

    public void save() {
        service.save(widget);
    }

    public String createNew() {
        widget = new Widget();
        return "edit";
    }

}
