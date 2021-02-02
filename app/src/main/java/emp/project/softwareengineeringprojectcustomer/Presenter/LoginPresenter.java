package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;
import java.util.HashSet;

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
    public final static String EMPTY_FIELD = "One or more fields are empty!";

    @Override
    public void onLoginButtonClicked(String username, String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayProgressLoader();
                    HashSet<CustomerModel.VALIDITY> validity = model.validateLogin(username, password);
                    for(CustomerModel.VALIDITY valids: validity) {
                        switch (valids) {
                            case EMPTY_FIELD:
                                view.setErrorUsername();
                                view.setErrorPassword();
                                view.hideProgressLoader();
                                view.onError(EMPTY_FIELD);
                                break;
                            case EMPTY_FIELD_USERNAME:
                                view.setErrorUsername();
                                view.hideProgressLoader();
                                view.onError(EMPTY_FIELD);
                                break;
                            case EMPTY_PASSWORD:
                                view.setErrorPassword();
                                view.onError(EMPTY_FIELD);
                                view.hideProgressLoader();
                                break;
                            case VALID_FIELD_USERNAME:
                                view.removeErrorUsername();
                                view.hideProgressLoader();
                                break;
                            case VALID_PASSWORD:
                                view.removeErrorPassword();
                                view.hideProgressLoader();
                                break;
                            case VALID:
                                view.displayProgressLoader();
                                view.removeErrorUsername();
                                view.removeErrorPassword();
                                if (service.fetchCustomerLoginCredentials(username, password)) {
                                    view.onSuccess();
                                } else {
                                    view.onError(USER_NOT_FOUND);
                                    view.hideProgressLoader();
                                }
                                view.hideProgressLoader();
                        }
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
