package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class UserProfilePresenter implements IUser.IUserPresenter {

    private IUser.IUserView view;
    private IUser.IUserService service;

    public UserProfilePresenter(IUser.IUserView view, IUser.IUserService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onFloatingUpdateButtonClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
    }

    @Override
    public void loadCredentials() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayLoader();
                    CustomerModel userModel = service.fetchUserCredentials();
                    view.displayUserCredentials(userModel);
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
