package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

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
        void insertOrdersToDB() throws SQLException, ClassNotFoundException;

        void insertToSpecificOrdersDB(String productName,String totalNumberOfOrders) throws ClassNotFoundException, SQLException;
    }
}
