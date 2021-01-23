package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.RegisterPresenter;


public class RegisterPresenterTest {
    IRegister.IRegisterView view;
    IRegister.IRegisterService service;
    IRegister.IRegisterPresenter presenter;

    @Before
    public void setUp() {
        view = new MockRegisterView();
        service = new MockRegisterService();
        presenter = new RegisterPresenter(view, new CustomerModel(), service);
    }

    @Test
    public void testSuccess() throws InterruptedException {
        presenter.onRegisterButtonClicked(
                "sample",
                "sample",
                "sample",
                "sample",
                "sample",
                new InputStream() {
                    @Override
                    public int read() {
                        return 0;
                    }
                });
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).pass_success);
    }

    @Test
    public void testErrorOnEnterAllFields() throws InterruptedException {
        presenter.onRegisterButtonClicked("",
                "",
                "",
                "",
                "",
                new InputStream() {
                    @Override
                    public int read() {
                        return 0;
                    }
                });
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).pass_enter_all_fields);
    }

    @Test
    public void testErrorOnPasswordNotEqual() throws InterruptedException {
        presenter.onRegisterButtonClicked("asd",
                "as",
                "dsa",
                "asd",
                "asd",
                new InputStream() {
                    @Override
                    public int read() {
                        return 0;
                    }
                });
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).pass_password_not_equal);
    }

    @Test
    public void testErrorOnEmptyImage() throws InterruptedException {
        presenter.onRegisterButtonClicked("asd",
                "as",
                "dsa",
                "asd",
                "asd",
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).pass_empty_image);
    }

    @Test
    public void testDisplayPhoneGallery() throws InterruptedException {
        presenter.onImageButtonClicked();
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).isGalleryDisplaying);
    }

    static class MockRegisterView implements IRegister.IRegisterView {
        boolean pass_success;
        boolean pass_enter_all_fields;
        boolean pass_password_not_equal;
        boolean pass_empty_image;
        boolean isGalleryDisplaying;

        @Override
        public void onSuccess() {
            if (MockRegisterService.mockDatabase.size() == 1) {
                pass_success = true;
            }
        }

        @Override
        public void onError(String errorMessage) {
            switch (errorMessage) {
                case RegisterPresenter.ENTER_ALL_FIELDS:
                    pass_enter_all_fields = true;
                    break;
                case RegisterPresenter.PASSWORD_NOT_EQUAL:
                    pass_password_not_equal = true;
                    break;
                case RegisterPresenter.EMPTY_IMAGE:
                    pass_empty_image = true;
                    break;
            }
        }

        @Override
        public void displayLoadingCircle() {

        }

        @Override
        public void hideLoadingCircler() {

        }

        @Override
        public void loadImageFromGallery() {
            isGalleryDisplaying = true;
        }

        @Override
        public void setErrorUsername() {

        }

        @Override
        public void setErrorPassword_1() {

        }

        @Override
        public void setErrorPassword_2() {

        }

        @Override
        public void setErrorEmail() {

        }

        @Override
        public void setErrorFullname() {

        }

        @Override
        public void removeErrorUsername() {

        }

        @Override
        public void removeErrorPassword_1() {

        }

        @Override
        public void removeErrorPassword_2() {

        }

        @Override
        public void removeErrorEmail() {

        }

        @Override
        public void removeErrorFullname() {

        }

        @Override
        public void setErrorOnNotEqualPassword() {

        }

        @Override
        public void removeErrorEqualPassword() {

        }
    }

    static class MockRegisterService implements IRegister.IRegisterService {
        static List<CustomerModel> mockDatabase = new ArrayList<>();

        @Override
        public void insertCustomerToDB(CustomerModel model) {
            model = new CustomerModel(
                    model.getUser_username(),
                    model.getUser_password(),
                    model.getUser_fullname(),
                    model.getUser_status(),
                    model.getUser_email(),
                    model.getInputStream());
            mockDatabase.add(model);
        }
    }
}
