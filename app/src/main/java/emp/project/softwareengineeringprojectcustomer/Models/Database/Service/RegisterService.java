package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class RegisterService implements IRegister.IRegisterService {

    private static RegisterService SINGLE_INSTANCE = null;

    public static RegisterService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new RegisterService();
        }
        return SINGLE_INSTANCE;
    }

    private RegisterService() {

    }

    @Override
    public void insertCustomerToDB(CustomerModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlInsert = "INSERT INTO customer_table(user_username,user_password,user_fullname,user_status,customer_email,profile_picture,user_code)" +
                "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setString(1, model.getUser_username());
        preparedStatement.setString(2, model.getUser_password());
        preparedStatement.setString(3, model.getUser_fullname());
        preparedStatement.setString(4, model.getUser_status());
        preparedStatement.setString(5, model.getUser_email());
        preparedStatement.setBlob(6, model.getInputStream());
        preparedStatement.setString(7, model.getCode());
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

}
