package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;

public class CheckoutPresenter implements ICheckout.ICheckoutPresenter {
    private ICheckout.ICheckoutView view;
    private ICheckout.ICheckoutService service;

    public CheckoutPresenter(ICheckout.ICheckoutView view, ICheckout.ICheckoutService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (Integer.parseInt(CartModel.getInstance().getTotalNumberOfOrders()) <= 0) {
                    view.displayEmptyCart();
                } else {
                    view.hideEmptyCart();
                }
                view.displayProgressBar();
                view.displayCartOrders();
                view.hideProgressBar();
            }
        });
        thread.start();
    }

    @Override
    public void loadCartTotals() {
        view.displayCartValues();
    }

    private static final String NO_ORDERS = "No Orders Found!";

    @Override
    public void onCheckoutButtonClicked() {
        if (Integer.parseInt(CartModel.getInstance().getTotalNumberOfOrders()) <= 0) {
            view.displayErrorMessage(NO_ORDERS);
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    view.displayProgressBar();
                    try {
                        service.insertOrdersToDB();
                        for (ProductModel model : CartModel.getInstance().getCartValues()) {
                            service.insertToSpecificOrdersDB(model.getProduct_name(), model.getTotal_number_products_orders());
                        }
                        CartModel.getInstance().removeAllValuesOnCart();
                        view.displaySuccessfullPrompt();
                        view.displayCartOrders();
                        view.displayCartValues();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    view.hideProgressBar();
                }
            });
            thread.start();
        }
    }
}
