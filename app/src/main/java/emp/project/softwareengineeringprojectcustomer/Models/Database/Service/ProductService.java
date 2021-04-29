package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IStrictMode;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class ProductService implements IStrictMode {
    private static ProductService instance = null;
    private ProductService() {

    }
    public static ProductService getInstance() {
        if(instance == null) {
            instance = new ProductService();
        }
        return instance;
    }
    public ProductModel fetchProduct(int productID) throws SQLException, ClassNotFoundException {
        strictMode();
        ProductModel productModel = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetProducts = "SELECT * FROM products_table WHERE product_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetProducts);
        preparedStatement.setInt(1, productID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            productModel = new ProductModel(resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("product_price"),
                    resultSet.getBlob("product_picture"),
                    resultSet.getString("product_stocks"),
                    resultSet.getString("product_category"));
        }
        return productModel;
    }
}
