package platform.web.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import platform.api.Product;
import platform.service.api.ProductService;
import platform.web.JSFHelper;

/**
 *
 * @author rodrigo
 */
@Named
@SessionScoped
public class ProductController implements Serializable {

    @Inject
    private ProductService service;

    private Product product = new Product();
    private List<Product> productList;

    @PostConstruct
    public void init() {
        product = new Product();
        productList = new ArrayList<>();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return service.list();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void delete(Product product) {
        service.delete(product.getKey());
    }

    public String edit(Product product) {
        this.product = service.get(product.getKey());
        return "edit";
    }

    public String save() {
        service.create(product);
        JSFHelper.addSuccessMessage("Product saved successfuly.");
        return "/pages/product/list";
    }

    public String createNew() {
        product = new Product();
        return "/pages/product/edit";
    }

}
