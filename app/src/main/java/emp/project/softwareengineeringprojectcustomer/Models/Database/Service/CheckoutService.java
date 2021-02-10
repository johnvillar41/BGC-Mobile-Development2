package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class CheckoutService implements ICheckout.ICheckoutService {
    private static CheckoutService instance;

    private CheckoutService() {

    }

    public static CheckoutService getInstance() {
        if (instance == null) {
            instance = new CheckoutService();
        }
        return instance;
    }
    /**
     * This function will insert orders in customer_orders_table
     * TODO: FIX this----------------------------------------------------------------------------------------------------
     * Alternative Solution:
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static final String INITIAL_ORDER_STATUS = "Processing";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void insertOrdersToDB() throws SQLException, ClassNotFoundException {
        strictMode();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String insertOrdersToDb = "INSERT INTO customer_orders_table(customer_name,customer_email,order_total_price,order_status,order_date,total_number_of_orders)" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertOrdersToDb);
        preparedStatement.setString(1, UserCredentials.getInstance().getUsername());
        preparedStatement.setString(2, UserCredentials.getInstance().getUserEmail());
        preparedStatement.setString(3, String.valueOf(CartModel.getInstance().calculateTotalOrderValues()));
        preparedStatement.setString(4, INITIAL_ORDER_STATUS);
        preparedStatement.setString(5, dtf.format(now));
        preparedStatement.setString(6, CartModel.getInstance().getTotalNumberOfOrders());
        preparedStatement.execute();
    }

    /**
     * This will insert orders in the specific orders table
     *
     * @param productName
     */
    @Override
    public void insertToSpecificOrdersDB(String productName, String totalNumberOfOrders) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String order_id = String.valueOf(checkForHighestOrderIdinDB());
        String product_id =  getProductId(productName);

        String insertOrdersToSpecific = "INSERT INTO specific_orders_table(order_id,product_id,total_orders)VALUES(" +
                "?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertOrdersToSpecific);
        preparedStatement.setString(1, order_id);
        preparedStatement.setString(2,product_id);
        preparedStatement.setString(3, totalNumberOfOrders);

        preparedStatement.execute();
        updateProductTotalinDB(totalNumberOfOrders,product_id);

        //Close Db Connections
        preparedStatement.close();
        connection.close();
    }

    private String getProductId(String productName) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGet = "SELECT product_id FROM products_table WHERE product_name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGet);
        preparedStatement.setString(1, productName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String product_id = resultSet.getString("product_id");
            preparedStatement.close();
            connection.close();
            return product_id;
        } else {
            preparedStatement.close();
            connection.close();
            return null;
        }
    }

    private Integer checkForHighestOrderIdinDB() throws ClassNotFoundException, SQLException {
        strictMode();
        int highestNUm = 0;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlCheckHighestID = "SELECT order_id FROM customer_orders_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCheckHighestID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt(1) > highestNUm) {
                highestNUm = resultSet.getInt(1);
            }
        }
        connection.close();
        preparedStatement.close();
        return highestNUm;
    }
    /**
     * -----------------------------------------------------------------------------------------------------------------------
     */

    private void updateProductTotalinDB(String product_orders,String product_id) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String updateTable = "UPDATE products_table SET product_stocks=product_stocks-? WHERE product_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateTable);
        preparedStatement.setString(1,product_orders);
        preparedStatement.setString(2,product_id);
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

}
