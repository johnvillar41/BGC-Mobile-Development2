package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;

public class LoginPresenter implements ILogin.ILoginPresenter {
    private ILogin.ILoginView view;
    private ILogin.ILoginService service;


    public LoginPresenter(ILogin.ILoginView view, ILogin.ILoginService service) {
        this.view = view;
        this.service = service;
    }

    public final static String USER_NOT_FOUND = "User not found!";
    public final static String USER_PENDING = "User still pending!";

    @Override
    public void onLoginButtonClicked(String username, String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressLoader();
                if (view.displayErrors()) {
                    try {
                        if (service.fetchCustomerLoginCredentials(username, password).equals(ILogin.ILoginService.LoginStatus.ACTIVE)) {
                            view.onSuccess();
                        } else if (service.fetchCustomerLoginCredentials(username, password).equals(ILogin.ILoginService.LoginStatus.PENDING)) {
                            view.displayPopupConfirmation();
                            view.displaySnackBarMessage(USER_PENDING);
                        } else {
                            view.displaySnackBarMessage(USER_NOT_FOUND);
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

    public static final String CONGRATULATIONS = "Congratulations";
    public static final String CONGRATULATIONS_MSG = "Congratulations you can now login";
    public static final String INVALID_CODE = "Invalid code!";

    @Override
    public void onSubmitCodeButtonClicked(String code, String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (service.validateCode(code, username)) {
                        service.updateUserStatus(username);
                        view.displaySnackBarMessage(CONGRATULATIONS);
                        view.displayStatusMessage(CONGRATULATIONS_MSG);
                    } else {
                        view.displaySnackBarMessage(INVALID_CODE);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
