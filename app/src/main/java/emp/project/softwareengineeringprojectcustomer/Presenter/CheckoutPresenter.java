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
                view.displayProgressLoader();
                view.displayCartOrders();
                view.hideProgressLoader();
            }
        });
        thread.start();
    }

    @Override
    public void onCheckoutButtonClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressLoader();
                try {
                    service.insertOrdersToDB();
                    for (ProductModel model : CartModel.getInstance().getCartValues()) {
                        service.insertToSpecificOrdersDB(model.getProduct_name(), model.getTotal_number_products_orders());
                    }
                    CartModel.getInstance().removeAllValuesOnCart();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressLoader();
            }
        });
        thread.start();
    }
}
