package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public interface IHome {
    interface IHomeView {
        void displayProgressBar();

        void hideProgressBar();

        void displayRecyclerViewCategory(List<String>categories);

        void displayRecyclerViewHomeProducts(List<ProductModel>productModelLists);
    }

    interface IHomePresenter {
        void loadCategories();

        void onCategoryButtonClicked(String category);
    }

    interface IHomeService extends IStrictMode{
        List<ProductModel> getProducts(String category) throws ClassNotFoundException, SQLException;

        List<String> getCategories() throws ClassNotFoundException, SQLException;
    }
}
