package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
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
    private TrackOrderService(){

    }

    @Override
    public List<CustomerOrdersModel> fetchOrdersFromDB() throws ClassNotFoundException, SQLException {
        SpecificOrdersModel specificOrdersModel = null;
        String order_id;
        strictMode();
        List<CustomerOrdersModel> ordersList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlfetch = "SELECT * FROM customer_orders_table WHERE customer_name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlfetch);
        preparedStatement.setString(1, UserCredentials.getInstance().getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            order_id = resultSet.getString("order_id");

            String sqlfetchSpecificOrders = "SELECT * FROM specific_orders_table WHERE order_id=?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlfetchSpecificOrders);
            preparedStatement1.setString(1, order_id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {

                String sqlFetchProduct = "SELECT product_name, product_picture,product_price FROM products_table WHERE product_id=?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sqlFetchProduct);
                preparedStatement2.setString(1, resultSet1.getString("product_id"));
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    String product_name = resultSet2.getString(1);
                    Blob product_picture = resultSet2.getBlob(2);
                    specificOrdersModel = new SpecificOrdersModel(
                            resultSet1.getString("order_id"),
                            resultSet1.getString("product_id"),
                            resultSet1.getString("total_orders"),
                            product_name,
                            product_picture,
                            resultSet2.getString("product_price"));
                }
            }
            CustomerOrdersModel customerOrdersModel = new CustomerOrdersModel(
                    resultSet.getString("order_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("customer_email"),
                    resultSet.getString("order_total_price"),
                    resultSet.getString("order_status"),
                    resultSet.getString("order_date"),
                    resultSet.getString("total_number_of_orders"),
                    specificOrdersModel
            );
            ordersList.add(customerOrdersModel);
        }
        return ordersList;
    }
}
