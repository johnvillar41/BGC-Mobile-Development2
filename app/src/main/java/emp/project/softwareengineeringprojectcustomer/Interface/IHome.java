package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public interface IHome {
    interface IHomeView extends IBaseView {
        void displayProgressBarCategories();

        void hideProgressBarCategories();

        void displayRecyclerViewCategory(List<String> categories);

        void displayRecyclerViewHomeProducts(List<ProductModel> productModelLists);

        void displayMessage(String message);

        void displayTotalNumberCart(String totalNumberOfOrders);
    }

    interface IHomePresenter {
        void loadCategories();

        void onCategoryButtonClicked(String category);

        void loadProducts();

        void onConfirmButtonClicked(String totalNumberOrders, ProductModel model);
    }

    interface IHomeService extends IStrictMode {
        List<ProductModel> getProducts(String category) throws ClassNotFoundException, SQLException;

        List<String> getCategories() throws ClassNotFoundException, SQLException;

        Integer checkIfProductIsEnough(String product_id) throws ClassNotFoundException, SQLException;
    }
}
