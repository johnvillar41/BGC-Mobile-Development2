package emp.project.softwareengineeringprojectcustomer;

import android.annotation.SuppressLint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class UserCredentials implements IStrictMode {
    private String username;
    private static UserCredentials instance;
    private static boolean isLoggedIn;

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

    public void logoutAccount() {
        isLoggedIn = false;
    }

    public void loginAccount() {
        isLoggedIn = true;
    }

    public Boolean checkLoginStatus() {
        return isLoggedIn;
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

    public Integer getUserID() throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME,USER,PASS);
        String sqlQuery = "SELECT user_id FROM customer_table WHERE user_username=?";
        strictMode();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("user_id");
        } else {
            return null;
        }
    }
}
