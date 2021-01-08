package emp.project.softwareengineeringprojectcustomer.Interface;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public interface IHome {
    interface IHomeView {
        void displayProgressBar();

        void hideProgressBar();

        void displayRecyclerViewCategory();

        void displayRecyclerViewHome();
    }

    interface IHomePresenter {
        void onCategoryButtonClicked(String category);
    }

    interface IHomeService {
        List<ProductModel> getProducts(String category);

        List<String> getCategories();
    }
}
