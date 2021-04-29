package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;

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

    interface ICheckoutService extends IStrictMode{
        void insertOrdersToDB() throws SQLException, ClassNotFoundException;

        void insertToSpecificOrdersDB(SpecificOrdersModel specificOrdersModel) throws ClassNotFoundException, SQLException;
    }
}
