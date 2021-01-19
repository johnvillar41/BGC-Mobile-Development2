package emp.project.softwareengineeringprojectcustomer.Models.Bean;

public class SpecificOrdersModel {
    private String order_id,product_id,total_orders;

    public SpecificOrdersModel(String order_id, String product_id, String total_orders) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.total_orders = total_orders;
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
}
