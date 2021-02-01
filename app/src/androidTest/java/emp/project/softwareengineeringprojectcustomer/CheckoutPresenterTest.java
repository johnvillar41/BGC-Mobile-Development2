package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.CheckoutPresenter;

import static emp.project.softwareengineeringprojectcustomer.CheckoutPresenterTest.MockProduct.PRODUCT_NAME;
import static emp.project.softwareengineeringprojectcustomer.CheckoutPresenterTest.MockProduct.PRODUCT_TOTAL_ORDERS;

public class CheckoutPresenterTest {
    ICheckout.ICheckoutView view;
    ICheckout.ICheckoutService service;
    ICheckout.ICheckoutPresenter presenter;

    @Before
    public void setUp() {
        view = new MockCheckoutView();
        service = new MockCheckoutService();
        presenter = new CheckoutPresenter(view, service);
    }

    @Test
    public void testLoadOrdersEmptyCart() throws InterruptedException {
        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockCheckoutView) view).is_emptyCart_showing);
    }

    @Test
    public void testLoadOrdersNotEmptyCart() throws InterruptedException {
        CartModel.getInstance().addToCart(new ProductModel());
        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockCheckoutView) view).is_emptyCart_not_showing);
        CartModel.getInstance().removeAllValuesOnCart();
    }

    @Test
    public void testDisplayProgressLoader() throws InterruptedException {
        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockCheckoutView) view).isProgressLoaderShowing);
    }

    @Test
    public void testHideProgressLoader() throws InterruptedException {
        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockCheckoutView) view).isProgressLoaderNotShowing);
    }


    public void testCheckout() throws InterruptedException {
        CartModel.getInstance().addToCart(new ProductModel(null,PRODUCT_NAME.getVal(),null,PRODUCT_TOTAL_ORDERS.getVal(),null,null,null));
        presenter.onCheckoutButtonClicked();
        Thread.sleep(1000);
        Assert.assertTrue(((MockCheckoutService)service).pass);
        CartModel.getInstance().removeAllValuesOnCart();
    }

    enum MockProduct {
        PRODUCT_NAME("Product"),
        PRODUCT_TOTAL_ORDERS("5");

        private String val;

        MockProduct(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    static class MockCheckoutView implements ICheckout.ICheckoutView {
        boolean is_emptyCart_showing;
        boolean is_emptyCart_not_showing;
        boolean isProgressLoaderShowing;
        boolean isProgressLoaderNotShowing;

        @Override
        public void displayCartOrders() {

        }

        @Override
        public void displayProgressLoader() {
            isProgressLoaderShowing = true;
        }

        @Override
        public void hideProgressLoader() {
            isProgressLoaderNotShowing = true;
        }

        @Override
        public void displaySuccessfullPrompt() {

        }

        @Override
        public void displayCartValues() {

        }

        @Override
        public void displayErrorMessage(String errorMessage) {

        }

        @Override
        public void displayEmptyCart() {
            is_emptyCart_showing = true;
        }

        @Override
        public void hideEmptyCart() {
            is_emptyCart_not_showing = true;
        }
    }

    static class MockCheckoutService implements ICheckout.ICheckoutService {
        boolean pass;

        @Override
        public void insertOrdersToDB() {

        }

        @Override
        public void insertToSpecificOrdersDB(String productName, String totalNumberOfOrders) {
            if(productName.equals(MockProduct.PRODUCT_NAME.getVal()) && totalNumberOfOrders.equals(MockProduct.PRODUCT_TOTAL_ORDERS.getVal())){
                pass = true;
            }
        }
    }
}
