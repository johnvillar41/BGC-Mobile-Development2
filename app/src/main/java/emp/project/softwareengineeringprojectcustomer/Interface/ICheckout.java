package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

public interface ICheckout {
    interface ICheckoutView extends ICart{
        void displayCartOrders();

        void displayProgressLoader();

        void hideProgressLoader();

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

        void insertToSpecificOrdersDB(String productName,String totalNumberOfOrders) throws ClassNotFoundException, SQLException;
    }
}
