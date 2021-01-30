package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    List<String> MockVal = new ArrayList<>();

    @Before
    public void setUp() {
        view = new MockRegisterView();
        service = new MockRegisterService();
        presenter = new RegisterPresenter(view, new CustomerModel(), service);

        MockVal.add("asd");
        MockVal.add("asd");
        MockVal.add("asd");
        MockVal.add("asd");
        MockVal.add("asd@gmail.com");
    }

    @Test
    public void testDisplayError() throws InterruptedException {
        presenter.onRegisterButtonClicked(MockVal, new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        Thread.sleep(1000);
        Assert.assertTrue(((MockRegisterView)view).isErrorDisplayed);
    }

    static class MockRegisterView implements IRegister.IRegisterView {
        boolean pass_success;
        boolean isGalleryDisplaying;
        boolean isErrorDisplayed;


        @Override
        public void onSuccess() {
            if (MockRegisterService.mockDatabase.size() == 1) {
                pass_success = true;
            }
        }

        @Override
        public void onError(String errorMessage) {

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
        public Boolean displayErrors() {
            isErrorDisplayed = true;
            return isErrorDisplayed;
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
