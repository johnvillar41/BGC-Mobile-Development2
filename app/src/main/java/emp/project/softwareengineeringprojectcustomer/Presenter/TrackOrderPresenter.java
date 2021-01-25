package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;

public class TrackOrderPresenter implements ITrackOrder.ITrackOrderPresenter {

    private ITrackOrder.ITrackOrderView view;
    private ITrackOrder.ITrackOrderService service;

    public TrackOrderPresenter(ITrackOrder.ITrackOrderView view, ITrackOrder.ITrackOrderService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CustomerOrdersModel> orderList=service.fetchOrdersFromDB();
                    view.displayLoader();
                    view.displayOrders(orderList);
                    view.hideLoader();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });thread.start();

    }
}
