package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;

public interface ITrackOrder {
    interface ITrackOrderView extends IBaseView {
        void displayOrders(List<CustomerOrdersModel> ordersList);

        void displayPopupSortBy();

        void displayEmptyResult();

        void hideEmptyResult();
    }

    interface ITrackOrderPresenter {
        void loadOrders();

        void onSortFloatinActionButtonClicked();

        void onButtonConfirmSortClicked(String sort_type);

        void onDateSortConfirmClicked(String dateString);
    }

    interface ITrackOrderService extends IStrictMode {
        List<CustomerOrdersModel> fetchOrdersFromDB() throws ClassNotFoundException, SQLException;

        List<CustomerOrdersModel> fetchSortedOrdersFromDB(String sort_type) throws ClassNotFoundException, SQLException;

        List<CustomerOrdersModel> fetchOrderByDate(String dateString) throws ClassNotFoundException, SQLException;
    }
}
