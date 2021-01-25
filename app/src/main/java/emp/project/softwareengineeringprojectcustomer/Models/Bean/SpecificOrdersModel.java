package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.sql.Blob;

public class SpecificOrdersModel {
    private String order_id,product_id,total_orders,product_name;
    private Blob product_image;
    public SpecificOrdersModel(String order_id, String product_id, String total_orders, String product_name, Blob product_image) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.total_orders = total_orders;
        this.product_name = product_name;
        this.product_image = product_image;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getTotal_orders() {
        return total_orders;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Blob getProduct_image() {
        return product_image;
    }
}
