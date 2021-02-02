package emp.project.softwareengineeringprojectcustomer.Presenter;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ICart;
import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class HomePresenter implements IHome.IHomePresenter, ICart {
    private IHome.IHomeView view;
    private IHome.IHomeService service;
    private Activity activity;

    public HomePresenter(IHome.IHomeView view, IHome.IHomeService service, Activity activity) {
        this.view = view;
        this.service = service;
        this.activity = activity;
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

    public static final String PRODUCT_NOT_ENOUGH = "Product is not enough!";
    public static final String SUCCESS_ADD_TO_CART = "Successfully added to cart!";
    public static final String SUCCESS_UPDATE_TO_CART = "Successfully updated to cart!";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConfirmButtonClicked(String totalNumberOrders, ProductModel model) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBarProducts();
                try {
                    if (Integer.parseInt(totalNumberOrders) <= service.checkIfProductIsEnough(model.getProduct_id())) {
                        if (CartModel.getInstance().getCartValues().contains(model)) {
                            for (ProductModel productModel : CartModel.getInstance().getCartValues()) {
                                if (productModel.equals(model)) {
                                    productModel.setTotal_number_products_orders(totalNumberOrders);
                                }
                            }
                            view.displayMessage(SUCCESS_UPDATE_TO_CART);
                        } else {
                            CartModel.getInstance().addToCart(model);
                            view.displayMessage(SUCCESS_ADD_TO_CART);
                            displayTotalCartNumbers(activity);
                        }
                    } else {
                        view.displayMessage(PRODUCT_NOT_ENOUGH);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                view.hideProgressBarProducts();
            }
        });
        thread.start();

    }
}
