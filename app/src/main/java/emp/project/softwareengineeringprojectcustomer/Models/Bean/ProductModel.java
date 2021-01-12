package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.sql.Blob;

public class ProductModel {
    private String product_id, product_name, product_price, product_stocks, product_category, product_description;
    private Blob product_picture;

    public ProductModel(String product_id, String product_name, String product_description,
                        String product_price, Blob product_picture, String product_stocks, String product_category) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.product_category = product_category;
        this.product_picture = product_picture;
        this.product_description = product_description;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_stocks() {
        return product_stocks;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getProduct_description() {
        return product_description;
    }

    public Blob getProduct_picture() {
        return product_picture;
    }
}
