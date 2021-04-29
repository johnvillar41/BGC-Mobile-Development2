package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;
import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class CustomerService implements IStrictMode {

    private static CustomerService instance = null;

    public static CustomerService getInstance() {
        if(instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    private CustomerService() {

    }

    public CustomerModel fetchCustomerDetails(int customerID) throws ClassNotFoundException, SQLException {
        strictMode();
        CustomerModel model = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlFetch = "SELECT * FROM customer_table WHERE user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlFetch);
        preparedStatement.setInt(1, customerID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            //String user_id, String user_username, String user_password, String user_fullname, String user_status, String user_email, Blob picture
            model = new CustomerModel(
                    resultSet.getString("user_id"),
                    resultSet.getString("user_username"),
                    resultSet.getString("user_password"),
                    resultSet.getString("user_fullname"),
                    resultSet.getString("user_status"),
                    resultSet.getString("customer_email"),
                    resultSet.getBlob("profile_picture")
            );

        }
        return model;
    }

}
