package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

public interface ICheckout {
    interface ICheckoutView extends IBaseView {
        void displayCartOrders();

        void displaySuccessfullPrompt();

        void displayCartValues();

        void displayErrorMessage(String errorMessage);

        void displayEmptyCart();

        void hideEmptyCart();
    }

    interface ICheckoutPresenter {
        void loadOrders();

        void loadCartTotals();

        void onCheckoutButtonClicked();
    }

    interface ICheckoutService {
        void insertOrdersToDB() throws SQLException, ClassNotFoundException;

        void insertToSpecificOrdersDB(String productName, String totalNumberOfOrders) throws ClassNotFoundException, SQLException;
    }
}
