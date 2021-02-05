package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class InformationService implements IInformation.IInformationService {

    private static InformationService instance = null;

    private InformationService() {

    }

    public static InformationService getInstance() {
        if (instance == null) {
            instance = new InformationService();
        }
        return instance;
    }

    @Override
    public List<InformationModel> fetchInformationData() throws ClassNotFoundException, SQLException {
        strictMode();
        List<InformationModel> informationModelList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetInformation = "SELECT * FROM information_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetInformation);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ProductModel productModel = null;
            String product_id = resultSet.getString("product_id");
            String product_information = resultSet.getString("product_information");
            String sqlGetProducts = "SELECT * FROM products_table WHERE product_id=?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlGetProducts);
            preparedStatement1.setString(1, product_id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                productModel = new ProductModel(
                        resultSet1.getString("product_id"),
                        resultSet1.getString("product_name"),
                        resultSet1.getString("product_description"),
                        resultSet1.getString("product_price"),
                        resultSet1.getBlob("product_picture"),
                        resultSet1.getString("product_stocks"),
                        resultSet1.getString("product_category")
                );
            }
            InformationModel informationModel = new InformationModel(product_information, productModel);
            informationModelList.add(informationModel);
        }
        return informationModelList;
    }
}
