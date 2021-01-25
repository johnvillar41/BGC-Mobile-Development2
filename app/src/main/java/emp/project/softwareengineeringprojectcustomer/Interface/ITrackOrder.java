package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;

public interface ITrackOrder {
    interface ITrackOrderView {
        void displayLoader();

        void hideLoader();

        void displayOrders(List<CustomerOrdersModel>ordersList);
    }

    interface ITrackOrderPresenter {
        void loadOrders();
    }

    interface ITrackOrderService extends IStrictMode{
        List<CustomerOrdersModel> fetchOrdersFromDB() throws ClassNotFoundException, SQLException;

    }
}
