package emp.project.softwareengineeringprojectcustomer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;

public class UserCredentials implements IStrictMode {
    private String username;
    private static UserCredentials instance;
    //TODO:
    //Session must be changed into a session variable or a shared preference but this will be used for the meantime
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
}
