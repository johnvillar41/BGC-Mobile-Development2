package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
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
        Assert.assertTrue(((MockLoginView) view).pass_error_user_not_found);
    }

    @Test
    public void testDisplayPopupForPendingUsers() throws InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView) view).isPopupDisplayed);
    }

    private static final String MOCK_USER = "sample";
    private static final String MOCK_PASS = "sample";

    static class MockLoginView implements ILogin.ILoginView {
        boolean pass_success;
        boolean pass_error_user_not_found;
        boolean isPopupDisplayed;

        @Override
        public void onSuccess() {
            pass_success = true;
        }

        @Override
        public void displaySnackBarMessage(String errorMessage) {
            if (errorMessage.equals(LoginPresenter.USER_NOT_FOUND)) {
                pass_error_user_not_found = true;
            }
        }

        @Override
        public void displayProgressLoader() {

        }

        @Override
        public void hideProgressLoader() {

        }

        @Override
        public Boolean displayErrors() {
            return null;
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
                new CustomerModel(MOCK_USER, "Password", "Name", "STatus", "Email", null, "Pending"),
                new CustomerModel("Sample2", "Password", "Name", "STatus", "Email", null, "Active")
        );

        @Override
        public void updateUserStatus(String username) {

        }

        @Override
        public LoginValidity fetchCustomerLoginCredentials(String username, String password) {
            for (CustomerModel customerModel : MOCK_CUSTOMER_DB) {
                if (username.equals(customerModel.getUser_username())) {
                    if (customerModel.getUser_status().equals("Pending")) {
                        return LoginValidity.PENDING;
                    } else {
                        return LoginValidity.ACTIVE;
                    }
                } else {
                    return LoginValidity.NOT_FOUND;
                }
            }
            return null;
        }

        @Override
        public Boolean validateCode(String code, String username) {
            return null;
        }
    }
}
