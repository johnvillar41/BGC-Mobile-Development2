package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter;

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
        Assert.assertTrue(MockHomeView.isCategoryDisplayed);
    }

    @Test
    public void testProductsDisplay() throws InterruptedException {
        presenter.onCategoryButtonClicked(MOCK_CATEGORY);
        Thread.sleep(1000);
        Assert.assertTrue(MockHomeView.isProductDisplayed);
    }

    @Test
    public void testDisplayProgressBarOnCategory() throws InterruptedException {
        presenter.loadCategories();
        Thread.sleep(1000);
        Assert.assertTrue(MockHomeView.isProgressBardisplayed);
    }

    private static final String MOCK_CATEGORY = "MockCategory";

    static class MockHomeView implements IHome.IHomeView {
        static boolean isCategoryDisplayed;
        static boolean isProductDisplayed;
        static boolean isProgressBardisplayed;
        static boolean isProgressBarhidden;

        @Override
        public void displayProgressBar() {
            isProgressBardisplayed = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressBarhidden = true;
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
    }

}
