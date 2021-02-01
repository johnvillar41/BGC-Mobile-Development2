package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
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
        Assert.assertTrue(((MockUserProfileView) view).isPopupDisplayed);
    }

    @Test
    public void testDisplayValuesOnUpdatePopup() throws InterruptedException {
        presenter.loadPopupValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView) view).isProgressBarPopupShowing);
        Assert.assertTrue(((MockUserProfileView) view).isPopupValuesDisplayed);
        Assert.assertTrue(((MockUserProfileView) view).isProgressBarPopupNotShowing);
    }

    @Test
    public void testLogout() throws InterruptedException {
        String[] mockArrTexts = {
                MockCustomerModel.USER_USERNAME.getVal(),
                MockCustomerModel.USER_PASSWORD.getVal(),
                MockCustomerModel.USER_FULLNAME.getVal(),
                MockCustomerModel.USER_EMAIL.getVal()
        };
        presenter.onUpdateProfileButtonClicked(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        }, mockArrTexts);
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView) view).isLoggedOut);
    }

    @Test
    public void testUpdateUser() throws InterruptedException {
        String[] mockArrTexts = {
                MockCustomerModel.USER_USERNAME.getVal(),
                MockCustomerModel.USER_PASSWORD.getVal(),
                MockCustomerModel.USER_FULLNAME.getVal(),
                MockCustomerModel.USER_EMAIL.getVal()
        };
        presenter.onUpdateProfileButtonClicked(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        }, mockArrTexts);
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserService) service).isUpdated);
    }

    @Test
    public void testProgressLoaderShowing() throws InterruptedException {
        presenter.loadCredentials();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView)view).isProgressLoaderShowing);
    }

    @Test
    public void testProgressNotLoaderShowing() throws InterruptedException {
        presenter.loadCredentials();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUserProfileView)view).isProgressLoaderNotShowing);
    }

    static class MockUserProfileView implements IUser.IUserView {
        boolean isUserProfileDisplayed;
        boolean isGalleryDisplayed;
        boolean isPopupDisplayed;
        boolean isPopupValuesDisplayed;
        boolean isLoggedOut;
        boolean isProgressBarPopupShowing;
        boolean isProgressBarPopupNotShowing;
        boolean isProgressLoaderShowing;
        boolean isProgressLoaderNotShowing;

        @Override
        public void displayLoader() {
            isProgressLoaderShowing = true;
        }

        @Override
        public void hideLoader() {
            isProgressLoaderNotShowing = true;
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
            if (userModel.getUser_id().equals(MockCustomerModel.USER_ID.getVal()) &&
                    userModel.getUser_username().equals(MockCustomerModel.USER_USERNAME.getVal()) &&
                    userModel.getUser_password().equals(MockCustomerModel.USER_PASSWORD.getVal()) &&
                    userModel.getUser_fullname().equals(MockCustomerModel.USER_FULLNAME.getVal()) &&
                    userModel.getUser_status().equals(MockCustomerModel.USER_STATUS.getVal()) &&
                    userModel.getUser_email().equals(MockCustomerModel.USER_EMAIL.getVal())) {
                isPopupValuesDisplayed = true;
            }
        }

        @Override
        public void displayProgressBarPopup() {
            isProgressBarPopupShowing = true;
        }

        @Override
        public void hideProgressBarPopup() {
            isProgressBarPopupNotShowing = true;
        }

        @Override
        public void logout() {
            isLoggedOut = true;
        }

        @Override
        public Boolean displayErrors() {
            return true;
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

        boolean isUpdated;

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
            if (userModel.getUser_username().equals(MockCustomerModel.USER_USERNAME.getVal()) &&
                    userModel.getUser_password().equals(MockCustomerModel.USER_PASSWORD.getVal()) &&
                    userModel.getUser_fullname().equals(MockCustomerModel.USER_FULLNAME.getVal()) &&
                    userModel.getUser_email().equals(MockCustomerModel.USER_EMAIL.getVal())) {
                isUpdated = true;
            }

        }
    }

}
