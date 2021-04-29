package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;

public class SpecificOrdersService implements IStrictMode {

    private static SpecificOrdersService instance = null;

    private SpecificOrdersService() {

    }

    public static SpecificOrdersService getInstance() {
        if (instance == null) {
            instance = new SpecificOrdersService();
        }
        return instance;
    }

    public List<SpecificOrdersModel> fetchSpecificOrders(int order_id) throws ClassNotFoundException, SQLException {
        strictMode();
        List<SpecificOrdersModel> specificOrdersModels = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlFetch = "SELECT * FROM specific_orders_table WHERE order_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlFetch);
        preparedStatement.setInt(1, order_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ProductModel product = ProductService.getInstance().fetchProduct(resultSet.getInt("product_id"));
            specificOrdersModels.add(
                    new SpecificOrdersModel(
                            resultSet.getInt("order_id"),
                            resultSet.getString("administrator_username"),
                            product,
                            resultSet.getInt("total_orders"),
                            resultSet.getInt("subtotal_price")
                    )
            );
        }
        return specificOrdersModels;
    }

}
