package emp.project.softwareengineeringprojectcustomer;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter;

import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_CATEGORY;
import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_DESCRIPTION;
import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_ID;
import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_NAME;
import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_PRICE;
import static emp.project.softwareengineeringprojectcustomer.HomePresenterTest.MOCK_PRODUCT_MODEL.PRODUCT_STOCKS;
import static emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter.PRODUCT_NOT_ENOUGH;
import static emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter.SUCCESS_ADD_TO_CART;

public class HomePresenterTest {
    IHome.IHomeView view;
    IHome.IHomeService service;
    IHome.IHomePresenter presenter;
    @Before
    public void setUp() {
        view = new MockHomeView();
        service = new MockHomeService();
        presenter = new HomePresenter(view, service);
    }

    @Test
    public void testCategoryDisplay() throws InterruptedException {
        presenter.loadCategories();
        Thread.sleep(1000);
        Assert.assertTrue(((MockHomeView) view).isCategoryDisplayed);
    }

    @Test
    public void testProductsDisplay() throws InterruptedException {
        presenter.onCategoryButtonClicked(MOCK_CATEGORY);
        Thread.sleep(1000);
        Assert.assertTrue(((MockHomeView) view).isProductDisplayed);
    }

    @Test
    public void testDisplayProgressBarOnCategory() throws InterruptedException {
        presenter.loadCategories();
        Thread.sleep(1000);
        Assert.assertTrue(((MockHomeView) view).isProgressBardisplayed);
    }

    @Test
    public void testNumberOfProductsMessageSuccess() throws InterruptedException {
        ProductModel mockModel = new ProductModel(PRODUCT_ID.getVal(), PRODUCT_NAME.getVal(), PRODUCT_DESCRIPTION.getVal(), PRODUCT_PRICE.getVal(), null, PRODUCT_STOCKS.getVal(), PRODUCT_CATEGORY.getVal());
        presenter.onConfirmButtonClicked(MOCK_TOTAL_NUMBER_ORDERS_PASS, mockModel);
        Thread.sleep(1000);
        Assert.assertTrue(((MockHomeView) view).isMessageDisplayed);
    }

    @Test
    public void testNumberOfProductsMessageFail() throws InterruptedException {
        ProductModel mockModel = new ProductModel(PRODUCT_ID.getVal(), PRODUCT_NAME.getVal(), PRODUCT_DESCRIPTION.getVal(), PRODUCT_PRICE.getVal(), null, PRODUCT_STOCKS.getVal(), PRODUCT_CATEGORY.getVal());
        presenter.onConfirmButtonClicked(MOCK_TOTAL_NUMBER_ORDERS__FAIL, mockModel);
        Thread.sleep(1000);
        Assert.assertTrue(((MockHomeView) view).isMessageDisplayed);
    }

    private static final String MOCK_TOTAL_NUMBER_ORDERS_PASS = "5";
    private static final String MOCK_TOTAL_NUMBER_ORDERS__FAIL = "100";
    private static final String MOCK_CATEGORY = "MockCategory";

    static class MockHomeView implements IHome.IHomeView {
        boolean isCategoryDisplayed;
        boolean isProductDisplayed;
        boolean isProgressBardisplayed;
        boolean isProgressBarhidden;
        boolean isMessageDisplayed;

        @Override
        public void displayProgressBarProducts() {

        }

        @Override
        public void hideProgressBarProducts() {
            isProgressBarhidden = true;
        }

        @Override
        public void displayProgressBarCategories() {
            isProgressBardisplayed = true;
        }

        @Override
        public void hideProgressBarCategories() {

        }

        @Override
        public void displayRecyclerViewCategory(List<String> categories) {
            if (categories.size() == 3) {
                isCategoryDisplayed = true;
            }
        }

        @Override
        public void displayRecyclerViewHomeProducts(List<ProductModel> productModelLists) {
            for (int i = 0; i < productModelLists.size(); i++) {
                if (productModelLists.get(i).getProduct_category().equals(MOCK_CATEGORY)) {
                    isProductDisplayed = true;
                } else {
                    isProductDisplayed = false;
                }
            }
        }

        @Override
        public void displayMessage(String message) {
            switch (message) {
                case SUCCESS_ADD_TO_CART:
                case PRODUCT_NOT_ENOUGH:
                    isMessageDisplayed = true;
                    break;
            }
        }

        @Override
        public void displayTotalCartNumbers() {

        }

        @Override
        public void hideTotalCartNumbers() {

        }
    }

    enum MOCK_PRODUCT_MODEL {
        PRODUCT_ID("1"),
        PRODUCT_NAME("MOCK_NAME"),
        PRODUCT_DESCRIPTION("MOCK_DESCRIPTION"),
        PRODUCT_PRICE("MOCK_PRICE"),
        PRODUCT_STOCKS("15"),
        PRODUCT_CATEGORY("MOCK_CATEGORY");

        private String val;

        MOCK_PRODUCT_MODEL(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    static class MockHomeService implements IHome.IHomeService {

        @Override
        public List<ProductModel> getProducts(String category) {
            List<ProductModel> mockList = new ArrayList<>();
            ProductModel model = new ProductModel(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MOCK_CATEGORY);
            ProductModel model2 = new ProductModel(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MOCK_CATEGORY);
            ProductModel model3 = new ProductModel(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MOCK_CATEGORY);
            mockList.add(model);
            mockList.add(model2);
            mockList.add(model3);
            return mockList;
        }

        @Override
        public List<String> getCategories() {
            List<String> mockCategoryList = new ArrayList<>();
            mockCategoryList.add("MockCategory1");
            mockCategoryList.add("MockCategory2");
            mockCategoryList.add("MockCategory3");
            return mockCategoryList;
        }

        @Override
        public Integer checkIfProductIsEnough(String product_id) {
            List<ProductModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new ProductModel(PRODUCT_ID.getVal(), PRODUCT_NAME.getVal(), PRODUCT_DESCRIPTION.getVal(), PRODUCT_PRICE.getVal(), null, PRODUCT_STOCKS.getVal(), PRODUCT_CATEGORY.getVal()));
            return Integer.parseInt(mockDatabase.get(0).getProduct_stocks());
        }
    }

}
