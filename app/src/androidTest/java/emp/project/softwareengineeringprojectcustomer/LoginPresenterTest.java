package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.SQLException;

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
        presenter = new LoginPresenter(view, new CustomerModel(), service);
    }

    @Test
    public void testSuccess() throws InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS);
        Thread.sleep(1000);
        Assert.assertTrue(MockLoginView.pass_success);
    }

    @Test
    public void testError_EMPTY_FIELD() throws InterruptedException {
        presenter.onLoginButtonClicked("", "");
        Thread.sleep(1000);
        Assert.assertTrue(MockLoginView.pass_error_empty_field);
    }

    @Test
    public void testError_USER_NOT_FOUND() throws InterruptedException {
        presenter.onLoginButtonClicked("john", "johnyy");
        Thread.sleep(1000);
        Assert.assertTrue(MockLoginView.pass_error_user_not_found);
    }

    private static final String MOCK_USER = "sample";
    private static final String MOCK_PASS = "sample";

    static class MockLoginView implements ILogin.ILoginView {
        static boolean pass_success;
        static boolean pass_error_empty_field;
        static boolean pass_error_user_not_found;
        @Override
        public void onSuccess() {
            pass_success = true;
        }

        @Override
        public void onError(String errorMessage) {
            if (errorMessage.equals(LoginPresenter.EMPTY_FIELD)) {
                pass_error_empty_field = true;
            }
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
        public void setErrorUsername() {

        }

        @Override
        public void setErrorPassword() {

        }

        @Override
        public void removeErrorUsername() {

        }

        @Override
        public void removeErrorPassword() {

        }
    }

    static class MockLoginService implements ILogin.ILoginService {

        @Override
        public boolean fetchCustomerLoginCredentials(String username, String password) {
            if (username.equals(MOCK_USER) && password.equals(MOCK_PASS)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
