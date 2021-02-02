package emp.project.softwareengineeringprojectcustomer;

import android.annotation.SuppressLint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;

public class UserCredentials implements IStrictMode {
    private String username;
    private static UserCredentials instance;
    public static boolean isLoggedIn;

    private UserCredentials() {

    }

    public static UserCredentials getInstance() {
        if (instance == null) {
            instance = new UserCredentials();
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUserEmail() throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetEmail = "SELECT customer_email FROM customer_table WHERE user_username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetEmail);
        preparedStatement.setString(1, getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("customer_email");
        } else {
            return null;
        }
    }
    /*TODO:
    Customer App Notes:
    * Add (on employee app): View all registered end-users/customers
    * (For clarification/reminder) "Slide Show" is for showing the tutorials of each product. Only accessible for ordered products, after the order is "finished". -> May encourage buying of products.
    * Displaying full name, email, and profile picture
    * Notification for adding products to the cart (red circle with a number -> number represents the current number of items in the cart)
     */
}
