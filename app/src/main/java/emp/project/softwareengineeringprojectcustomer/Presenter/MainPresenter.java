package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IMain;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class MainPresenter implements IMain.IMainPresenter {

    private IMain.IMainView view;
    private IMain.IMainService service;

    public MainPresenter(IMain.IMainView view, IMain.IMainService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadUserDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerModel model = service.fetchUserDetails();
                    view.displayUserDetails(model);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    public void loadCartNumber() {
        view.displayTotalNumberOfCartNumbers();
    }
}
