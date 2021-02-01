package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.UserProfilePresenter;

public class UserProfilePresenterTest {

    IUser.IUserView view;
    IUser.IUserService service;
    IUser.IUserPresenter presenter;

    @Before
    public void setUp() {
        view = new MockUserProfileView();
        service = new MockUserService();
        presenter = new UserProfilePresenter(view, service);
    }

    @Test
    public void testDisplayCredentials() throws InterruptedException {
        presenter.loadCredentials();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView) view).isUserProfileDisplayed);
    }

    @Test
    public void testDisplayPhoneGallery() {
        presenter.onSelectImageButtonClicked();
        Assert.assertTrue(((MockUserProfileView) view).isGalleryDisplayed);
    }
    @Test
    public void testDisplayUpdatePopup() throws InterruptedException {
        presenter.onFloatingUpdateButtonClicked();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView)view).isPopupDisplayed);
    }

    static class MockUserProfileView implements IUser.IUserView {
        boolean isUserProfileDisplayed;
        boolean isGalleryDisplayed;
        boolean isPopupDisplayed;

        @Override
        public void displayLoader() {

        }

        @Override
        public void hideLoader() {

        }

        @Override
        public void displayUserCredentials(CustomerModel userModel) {
            if (userModel.getUser_id().equals(MockCustomerModel.USER_ID.getVal()) &&
                    userModel.getUser_username().equals(MockCustomerModel.USER_USERNAME.getVal()) &&
                    userModel.getUser_password().equals(MockCustomerModel.USER_PASSWORD.getVal()) &&
                    userModel.getUser_fullname().equals(MockCustomerModel.USER_FULLNAME.getVal()) &&
                    userModel.getUser_status().equals(MockCustomerModel.USER_STATUS.getVal()) &&
                    userModel.getUser_email().equals(MockCustomerModel.USER_EMAIL.getVal())) {
                isUserProfileDisplayed = true;
            }
        }

        @Override
        public void displayUpdatePopup() {
            isPopupDisplayed = true;
        }

        @Override
        public void loadImageFromGallery() {
            isGalleryDisplayed = true;
        }

        @Override
        public void displayUserCredentialsPopup(CustomerModel userModel) {

        }

        @Override
        public void displayProgressBarPopup() {

        }

        @Override
        public void hideProgressBarPopup() {

        }

        @Override
        public void logout() {

        }

        @Override
        public Boolean displayErrors() {
            return null;
        }
    }

    enum MockCustomerModel {
        USER_ID("1"),
        USER_USERNAME("sample"),
        USER_PASSWORD("sample"),
        USER_FULLNAME("sample"),
        USER_STATUS("Active"),
        USER_EMAIL("sample@gmail.com");

        private String val;

        MockCustomerModel(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    static class MockUserService implements IUser.IUserService {
        @Override
        public CustomerModel fetchUserCredentials() {
            return new CustomerModel(
                    MockCustomerModel.USER_ID.getVal(),
                    MockCustomerModel.USER_USERNAME.getVal(),
                    MockCustomerModel.USER_PASSWORD.getVal(),
                    MockCustomerModel.USER_FULLNAME.getVal(),
                    MockCustomerModel.USER_STATUS.getVal(),
                    MockCustomerModel.USER_EMAIL.getVal(),
                    null
            );
        }

        @Override
        public void updateUserCredentials(CustomerModel userModel) {

        }
    }

}
