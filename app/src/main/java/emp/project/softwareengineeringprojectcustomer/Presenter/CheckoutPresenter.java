package emp.project.softwareengineeringprojectcustomer.Presenter;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;

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
        });thread.start();
    }

    @Override
    public void onCheckoutButtonClicked() {

    }
}
