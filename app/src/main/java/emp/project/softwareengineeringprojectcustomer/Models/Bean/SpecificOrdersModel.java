package emp.project.softwareengineeringprojectcustomer.Models.Bean;

public class SpecificOrdersModel {
    private int specific_orders_id;
    private Integer order_id;
    private String administrator;
    private ProductModel productModel;
    private int total_orders;
    private int subtotal_price;

    public SpecificOrdersModel(Integer order_id, String administrator, ProductModel productModel, int total_orders, int subtotal_price) {
        this.order_id = order_id;
        this.administrator = administrator;
        this.productModel = productModel;
        this.total_orders = total_orders;
        this.subtotal_price = subtotal_price;
    }

    public int getSpecific_orders_id() {
        return specific_orders_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getAdministrator() {
        return administrator;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public int getTotal_orders() {
        return total_orders;
    }

    public int getSubtotal_price() {
        return subtotal_price;
    }

    public void setSpecific_orders_id(int specific_orders_id) {
        this.specific_orders_id = specific_orders_id;
    }
}
