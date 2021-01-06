package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.DatabaseCredentials;
import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;

public class LoginService implements ILogin.ILoginService {

    private static LoginService SINGLE_INSTANCE = null;

    public static LoginService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new LoginService();
        }
        return SINGLE_INSTANCE;
    }

    private LoginService() {

    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public boolean fetchCustomerLoginCredentials(String username, String password) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DatabaseCredentials.DB_NAME, DatabaseCredentials.USER, DatabaseCredentials.PASS);
        String sqlGetCustomer = "SELECT * FROM customer_table WHERE user_username=? AND user_password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetCustomer);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.close();
            connection.close();
            resultSet.close();
            return true;
        } else {
            preparedStatement.close();
            connection.close();
            resultSet.close();
            return false;
        }
    }
}
