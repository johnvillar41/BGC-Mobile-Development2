package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class UserProfileService implements IUser.IUserService {

    private static UserProfileService instance = null;

    public static UserProfileService getInstance() {
        if (instance == null) {
            instance = new UserProfileService();
        }
        return instance;
    }

    private UserProfileService() {

    }

    @Override
    public CustomerModel fetchUserCredentials() throws ClassNotFoundException, SQLException {
        strictMode();
        CustomerModel model = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlFetch = "SELECT * FROM customer_table WHERE user_username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlFetch);
        preparedStatement.setString(1, UserCredentials.getInstance().getUsername());
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

    @Override
    public void updateUserCredentials(CustomerModel userModel) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        if (userModel.getInputStream() == null) {
            String sqlUpdate = "UPDATE customer_table SET user_username=?,user_password=?,user_fullname=?,customer_email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, userModel.getUser_username());
            preparedStatement.setString(2, userModel.getUser_password());
            preparedStatement.setString(3, userModel.getUser_fullname());
            preparedStatement.setString(4, userModel.getUser_email());
            preparedStatement.execute();
        } else {
            String sqlUpdate = "UPDATE customer_table SET user_username=?,user_password=?,user_fullname=?,customer_email=?,profile_picture=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, userModel.getUser_username());
            preparedStatement.setString(2, userModel.getUser_password());
            preparedStatement.setString(3, userModel.getUser_fullname());
            preparedStatement.setString(4, userModel.getUser_email());
            preparedStatement.setBlob(5, userModel.getInputStream());
            preparedStatement.execute();
        }
    }
}
