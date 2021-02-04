package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import emp.project.softwareengineeringprojectcustomer.Interface.IMain;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.MainPresenter;

public class MainMenuPresenterTest {
    IMain.IMainView view;
    IMain.IMainService service;
    IMain.IMainPresenter presenter;

    @Before
    public void setUp() {
        view = new MockMainView();
        service = new MockMainService();
        presenter = new MainPresenter(view, service);
    }

    @Test
    public void testDisplayUserDetails() throws InterruptedException {
        presenter.loadUserDetails();
        Thread.sleep(1000);
        Assert.assertTrue(((MockMainView) view).isUserDisplayed);
    }

    @Test
    public void testDisplayTotalNumberOfCartValues() {
        presenter.loadCartNumber();
        Assert.assertTrue(((MockMainView)view).isCartNumberDisplayed);
    }

    enum MockCustomerModel {
        USERNAME,
        FULLNAME,
        EMAIL,
    }

    static class MockMainView implements IMain.IMainView {
        boolean isUserDisplayed;
        boolean isCartNumberDisplayed;

        @Override
        public void displayUserDetails(CustomerModel userList) {
            if (userList.getUser_username().equals(MockCustomerModel.USERNAME.name()) &&
                    userList.getUser_fullname().equals(MockCustomerModel.FULLNAME.name()) &&
                    userList.getUser_email().equals(MockCustomerModel.EMAIL.name())) {
                isUserDisplayed = true;
            }
        }

        @Override
        public void displayTotalNumberOfCartNumbers() {
            isCartNumberDisplayed = true;
        }
    }

    static class MockMainService implements IMain.IMainService {


        @Override
        public CustomerModel fetchUserDetails() {
            return new CustomerModel(
                    MockCustomerModel.USERNAME.name(),
                    MockCustomerModel.FULLNAME.name(),
                    MockCustomerModel.EMAIL.name(),
                    null
            );
        }
    }
}
