package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IMain;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class MainService implements IMain.IMainService {

    private static MainService instance = null;

    public static MainService getInstance() {
        if (instance == null) {
            instance = new MainService();
        }
        return instance;
    }

    private MainService() {

    }

    @Override
    public CustomerModel fetchUserDetails() throws ClassNotFoundException, SQLException {
        strictMode();
        CustomerModel customerModel = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlFetchUser = "SELECT * FROM customer_table WHERE user_username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlFetchUser);
        preparedStatement.setString(1, UserCredentials.getInstance().getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            customerModel = new CustomerModel(
                    resultSet.getString("user_username"),
                    resultSet.getString("user_fullname"),
                    resultSet.getString("customer_email"),
                    resultSet.getBlob("profile_picture"));
        }
        return customerModel;
    }
}
