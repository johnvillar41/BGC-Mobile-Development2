package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class HomeService implements IHome.IHomeService {

    private static HomeService INSTANCE = null;

    public static HomeService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new HomeService();
        }
        return INSTANCE;
    }

    private HomeService() {

    }

    @Override
    public List<ProductModel> getProducts(String category) throws ClassNotFoundException, SQLException {
        strictMode();
        ProductModel model;
        List<ProductModel> productList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetProducts = "SELECT * FROM products_table WHERE product_category=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetProducts);
        preparedStatement.setString(1,category);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            model = new ProductModel(
                    resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("product_price"),
                    resultSet.getBlob("product_picture"),
                    resultSet.getString("product_stocks"),
                    resultSet.getString("product_category")
            );
            productList.add(model);
        }
        return productList;
    }

    @Override
    public List<String> getCategories() throws ClassNotFoundException, SQLException {
        strictMode();
        HashSet<String> categorySet = new HashSet<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetCategories = "SELECT product_category FROM products_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetCategories);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            categorySet.add(resultSet.getString("product_category"));
        }
        List<String> categoryList = new ArrayList<>(categorySet);
        return categoryList;
    }


}
