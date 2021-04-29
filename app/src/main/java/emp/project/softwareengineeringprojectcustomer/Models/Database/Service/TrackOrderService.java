package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class TrackOrderService implements ITrackOrder.ITrackOrderService {

    private static TrackOrderService instance = null;

    public static TrackOrderService getInstance() {
        if (instance == null) {
            instance = new TrackOrderService();
        }
        return instance;
    }

    private TrackOrderService() {

    }

    @Override
    public List<CustomerOrdersModel> fetchOrdersFromDB() throws ClassNotFoundException, SQLException {
        List<CustomerOrdersModel> customerOrdersModels = new ArrayList<>();
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlQuery = "SELECT * FROM customer_orders_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CustomerModel customerModel = CustomerService.getInstance().fetchCustomerDetails(resultSet.getInt("user_id"));
            List<SpecificOrdersModel> specificOrdersModelList = SpecificOrdersService.getInstance().fetchSpecificOrders(resultSet.getInt("order_id"));
            CustomerOrdersModel customerOrdersModel = new CustomerOrdersModel(customerModel,
                    resultSet.getInt("order_id"),
                    resultSet.getInt("order_total_price"),
                    resultSet.getString("order_status"),
                    resultSet.getString("order_date"),
                    resultSet.getInt("total_number_of_orders"), specificOrdersModelList);
            customerOrdersModels.add(customerOrdersModel);
        }
        return customerOrdersModels;
    }

    @Override
    public List<CustomerOrdersModel> fetchSortedOrdersFromDB(String sort_type) throws ClassNotFoundException, SQLException {
        List<CustomerOrdersModel> customerOrdersModels = new ArrayList<>();
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlQuery = "SELECT * FROM customer_orders_table WHERE user_id=? AND order_status=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1,UserCredentials.getInstance().getUserID().toString());
        preparedStatement.setString(2,sort_type);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CustomerModel customerModel = CustomerService.getInstance().fetchCustomerDetails(resultSet.getInt("order_id"));
            List<SpecificOrdersModel> specificOrdersModelList = SpecificOrdersService.getInstance().fetchSpecificOrders(resultSet.getInt("user_id"));
            CustomerOrdersModel customerOrdersModel = new CustomerOrdersModel(customerModel,
                    resultSet.getInt("order_id"),
                    resultSet.getInt("order_total_price"),
                    resultSet.getString("order_status"),
                    resultSet.getString("order_date"),
                    resultSet.getInt("total_number_of_orders"), specificOrdersModelList);
            customerOrdersModels.add(customerOrdersModel);
        }
        return customerOrdersModels;
    }

    @Override
    public List<CustomerOrdersModel> fetchOrderByDate(String dateString) throws ClassNotFoundException, SQLException {

        List<CustomerOrdersModel> customerOrdersModels = new ArrayList<>();
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlfetch = "SELECT * FROM customer_orders_table WHERE user_id=? AND order_date=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlfetch);
        preparedStatement.setString(1,UserCredentials.getInstance().getUserID().toString());
        preparedStatement.setString(2,dateString);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CustomerModel customerModel = CustomerService.getInstance().fetchCustomerDetails(resultSet.getInt("order_id"));
            List<SpecificOrdersModel> specificOrdersModelList = SpecificOrdersService.getInstance().fetchSpecificOrders(resultSet.getInt("user_id"));
            CustomerOrdersModel customerOrdersModel = new CustomerOrdersModel(customerModel,
                    resultSet.getInt("order_id"),
                    resultSet.getInt("order_total_price"),
                    resultSet.getString("order_status"),
                    resultSet.getString("order_date"),
                    resultSet.getInt("total_number_of_orders"), specificOrdersModelList);
            customerOrdersModels.add(customerOrdersModel);
        }
        return customerOrdersModels;
    }
}
