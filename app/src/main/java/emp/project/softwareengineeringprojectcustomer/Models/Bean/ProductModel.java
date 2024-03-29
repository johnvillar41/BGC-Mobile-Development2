package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.sql.Blob;
import java.util.Objects;

public class ProductModel {
    private String product_id, product_name, product_price, product_stocks, product_category, product_description;
    private Blob product_picture;
    private String total_number_products_orders;

    /**
     * This constructor will be used to get the products
     * from the database
     */
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

    /**
     * This constructor is for the cartModel
     */
    public ProductModel(String product_id, String product_name, String product_price, String product_stocks, String product_category,
                        String product_description, Blob product_picture, String total_number_products) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.product_category = product_category;
        this.product_description = product_description;
        this.product_picture = product_picture;
        this.total_number_products_orders = total_number_products;
    }

    public ProductModel() {
    }

    public String getTotal_number_products_orders() {
        return total_number_products_orders;
    }

    public void setTotal_number_products_orders(String total_number_products_orders) {
        this.total_number_products_orders = total_number_products_orders;
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

    public void setProduct_stocks(String product_stocks) {
        this.product_stocks = product_stocks;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(product_id, that.product_id) &&
                Objects.equals(product_name, that.product_name) &&
                Objects.equals(product_category, that.product_category) &&
                Objects.equals(product_description, that.product_description) &&
                Objects.equals(product_picture, that.product_picture);
    }

    @NotNull
    @Override
    public String toString() {
        return "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price='" + product_price + '\'' +
                ", product_stocks='" + product_stocks + '\'' +
                ", product_category='" + product_category + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_picture=" + product_picture +
                ", total_number_products='" + total_number_products_orders + '\'' +
                '}';
    }
}
