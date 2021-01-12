package emp.project.softwareengineeringprojectcustomer.Presenter;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.LoginService;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.LoginActivityView;

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
    public final static String EMPTY_FIELD = "One or more fields are empty!";

    @Override
    public void onLoginButtonClicked(String username, String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayProgressLoader();
                    CustomerModel.VALIDITY validity = model.validateLogin(username, password);
                    if (validity.equals(CustomerModel.VALIDITY.VALID)) {
                        if (service.fetchCustomerLoginCredentials(username, password)) {
                            view.onSuccess();
                        } else {
                            view.onError(USER_NOT_FOUND);
                            view.hideProgressLoader();
                        }

                    } else if (validity.equals(CustomerModel.VALIDITY.EMPTY_FIELD)) {
                        view.onError(EMPTY_FIELD);
                        view.hideProgressLoader();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
