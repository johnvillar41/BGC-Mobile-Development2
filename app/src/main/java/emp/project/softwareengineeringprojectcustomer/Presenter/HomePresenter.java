package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
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
                    view.displayProgressBarCategories();
                    List<String> categories = service.getCategories();
                    view.displayRecyclerViewCategory(categories);
                    view.hideProgressBarCategories();
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBarProducts();
                try {
                    List<ProductModel> productModelList = service.getProducts(category);
                    view.displayRecyclerViewHomeProducts(productModelList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressBarProducts();
            }
        });
        thread.start();

    }

    @Override
    public void loadProducts() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBarProducts();
                try {
                    List<ProductModel> productModelList = service.getProducts(service.getCategories().get(0));
                    view.displayRecyclerViewHomeProducts(productModelList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressBarProducts();
            }
        });
        thread.start();
    }

    private static final String PRODUCT_NOT_ENOUGH = "Product is not enough!";
    private static final String SUCCESS_ADD_TO_CART = "Successfully added to cart!";

    @Override
    public void onConfirmButtonClicked(String totalNumberOrders, ProductModel model) {
        try {
            if (Integer.parseInt(totalNumberOrders) < service.checkIfProductIsEnough(model.getProduct_id())) {
                ProductModel productModel = new ProductModel(
                        model.getProduct_id(),
                        model.getProduct_name(),
                        model.getProduct_price(),
                        model.getProduct_stocks(),
                        model.getProduct_category(),
                        model.getProduct_description(),
                        model.getProduct_picture(),
                        model.getTotal_number_products_orders());
                CartModel.getInstance().addToCart(productModel);
                view.displayMessage(SUCCESS_ADD_TO_CART);
            } else {
                view.displayMessage(PRODUCT_NOT_ENOUGH);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
