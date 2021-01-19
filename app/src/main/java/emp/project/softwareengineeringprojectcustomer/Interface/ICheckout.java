package emp.project.softwareengineeringprojectcustomer.Interface;

public interface ICheckout {
    interface ICheckoutView {
        void displayCartOrders();

        void displayProgressLoader();

        void hideProgressLoader();
    }

    interface ICheckoutPresenter {
        void loadOrders();

        void onCheckoutButtonClicked();
    }

    interface ICheckoutService {
        void insertOrdersToDB();
    }
}
