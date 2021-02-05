package emp.project.softwareengineeringprojectcustomer.Models.Bean;

public class InformationModel {
    private String product_information_tutorial;
    private ProductModel productModel;

    public InformationModel(String product_information_tutorial, ProductModel productModel) {
        this.product_information_tutorial = product_information_tutorial;
        this.productModel = productModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public String getProduct_information_tutorial() {
        return product_information_tutorial;
    }
}
