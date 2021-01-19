package emp.project.softwareengineeringprojectcustomer.Models.Bean;

public class CustomerOrdersModel {
    private String order_id, customer_name, customer_email, order_price, order_status, order_date, total_number_of_orders;

    public CustomerOrdersModel(String order_id, String customer_name, String customer_email, String order_price, String order_status, String order_date, String total_number_of_orders) {
        this.order_id = order_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.order_price = order_price;
        this.order_status = order_status;
        this.order_date = order_date;
        this.total_number_of_orders = total_number_of_orders;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getOrder_price() {
        return order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getTotal_number_of_orders() {
        return total_number_of_orders;
    }
}
