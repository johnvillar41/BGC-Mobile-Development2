package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.util.List;

public class CustomerOrdersModel {
    private int order_id;
    private CustomerModel customerModel;
    private int order_total_price;
    private String orderStatus;
    private String order_date;
    private int total_number_of_orders;
    private List<SpecificOrdersModel> specificOrdersModel;

    public CustomerOrdersModel(CustomerModel customerModel, int order_id, int order_total_price, String orderStatus, String order_date, int total_number_of_orders, List<SpecificOrdersModel> specificOrdersModel) {
        this.customerModel = customerModel;
        this.order_id = order_id;
        this.order_total_price = order_total_price;
        this.orderStatus = orderStatus;
        this.order_date = order_date;
        this.total_number_of_orders = total_number_of_orders;
        this.specificOrdersModel = specificOrdersModel;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public int getOrder_total_price() {
        return order_total_price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrder_date() {
        return order_date;
    }

    public int getTotal_number_of_orders() {
        return total_number_of_orders;
    }

    public List<SpecificOrdersModel> getSpecificOrdersModel() {
        return specificOrdersModel;
    }
}
