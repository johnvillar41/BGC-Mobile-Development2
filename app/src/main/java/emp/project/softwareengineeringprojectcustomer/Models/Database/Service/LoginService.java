package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void updateUserStatus(String username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlUpdateUserStatus = "UPDATE customer_table SET user_status=? WHERE user_username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUserStatus);
        preparedStatement.setString(1,"Active");
        preparedStatement.setString(2,username);
        preparedStatement.execute();
    }

    @Override
    public LoginStatus fetchCustomerLoginCredentials(String username, String password) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetCustomer = "SELECT * FROM customer_table WHERE user_username LIKE BINARY ? AND user_password LIKE BINARY ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetCustomer);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if (resultSet.getString("user_status").equals("Pending")) {
                preparedStatement.close();
                connection.close();
                resultSet.close();
                return LoginStatus.PENDING;
            } else {
                preparedStatement.close();
                connection.close();
                resultSet.close();
                return LoginStatus.ACTIVE;
            }

        } else {
            preparedStatement.close();
            connection.close();
            resultSet.close();
            return LoginStatus.NOT_FOUND;
        }
    }

    @Override
    public Boolean validateCode(String code, String username) throws ClassNotFoundException, SQLException {
        strictMode();
        boolean validCode = false;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetCode = "SELECT user_code FROM customer_table WHERE user_username = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetCode);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if (code.equals(resultSet.getString("user_code"))) {
                validCode = true;
            } else {
                validCode = false;
            }
        }
        return validCode;
    }
}
