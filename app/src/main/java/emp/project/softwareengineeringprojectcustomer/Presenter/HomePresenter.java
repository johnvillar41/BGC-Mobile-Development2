package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class HomePresenter implements IHome.IHomePresenter {
    private IHome.IHomeView view;
    private IHome.IHomeService service;

    public HomePresenter(IHome.IHomeView view, IHome.IHomeService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadCategories() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayProgressBar();
                    List<String> categories = service.getCategories();
                    view.displayRecyclerViewCategory(categories);
                    view.hideProgressBar();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onCategoryButtonClicked(String category) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    List<ProductModel> productModelList = service.getProducts(category);
                    view.displayRecyclerViewHomeProducts(productModelList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });thread.start();

    }
}