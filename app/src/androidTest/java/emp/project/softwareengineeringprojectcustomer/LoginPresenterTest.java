package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.LoginPresenter;

public class LoginPresenterTest {
    ILogin.ILoginView view;
    ILogin.ILoginService service;
    ILogin.ILoginPresenter presenter;

    @Before
    public void setUp() {
        view = new MockLoginView();
        service = new MockLoginService();
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void testSuccess() throws InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView) view).pass_success);
    }

    @Test
    public void testError_USER_NOT_FOUND() throws InterruptedException {
        presenter.onLoginButtonClicked("john", "johnyy");
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView) view).isErrorSnackBarDisplayed);
    }

    @Ignore
    public void testDisplayPopupForPendingUsers() throws InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView) view).isPopupDisplayed);
    }

    private static final String MOCK_USER = "sample";
    private static final String MOCK_PASS = "sample";

    static class MockLoginView implements ILogin.ILoginView {
        boolean pass_success;
        boolean isErrorSnackBarDisplayed;
        boolean isPopupDisplayed;

        @Override
        public void onSuccess() {
            pass_success = true;
        }

        @Override
        public void displaySnackBarMessage(String errorMessage) {

            isErrorSnackBarDisplayed = true;

        }

        @Override
        public void displayProgressBar() {

        }

        @Override
        public void hideProgressBar() {

        }

        @Override
        public Boolean displayErrors() {
            return true;
        }

        @Override
        public void displayPopupConfirmation() {
            isPopupDisplayed = true;
        }

        @Override
        public void displayStatusMessage(String message) {

        }
    }

    static class MockLoginService implements ILogin.ILoginService {

        private static final List<CustomerModel> MOCK_CUSTOMER_DB = Arrays.asList(
                new CustomerModel(MOCK_USER, MOCK_PASS, "Name", "Pending", "Email", null, "code"),
                new CustomerModel("Sample2", "Password", "Name", "STatus", "Email", null, "code")
        );

        @Override
        public void updateUserStatus(String username) {

        }

        @Override
        public LoginStatus fetchCustomerLoginCredentials(String username, String password) {
            LoginStatus loginStatus = null;
            for (int i = 0; i < MOCK_CUSTOMER_DB.size(); i++) {
                if (username.equals(MOCK_CUSTOMER_DB.get(i).getUser_username())) {
                    if (MOCK_CUSTOMER_DB.get(i).getUser_status().equals("Pending")) {
                        loginStatus = LoginStatus.PENDING;
                    } else {
                        loginStatus = LoginStatus.ACTIVE;
                    }
                } else {
                    loginStatus = LoginStatus.NOT_FOUND;
                }
            }
            return loginStatus;
        }

        @Override
        public Boolean validateCode(String code, String username) {
            return null;
        }
    }
}
