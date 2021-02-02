package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class LoginPresenter implements ILogin.ILoginPresenter {
    private ILogin.ILoginView view;
    private ILogin.ILoginService service;
    private CustomerModel model;


    public LoginPresenter(ILogin.ILoginView view, CustomerModel model, ILogin.ILoginService service) {
        this.view = view;
        this.model = model;
        this.service = service;
    }

    public final static String USER_NOT_FOUND = "User not found!";

    @Override
    public void onLoginButtonClicked(String username, String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressLoader();
                if (view.displayErrors()) {

                    try {
                        if (service.fetchCustomerLoginCredentials(username, password)) {
                            view.onSuccess();
                        } else {
                            view.onError(USER_NOT_FOUND);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                view.hideProgressLoader();
            }
        });
        thread.start();

    }
}
