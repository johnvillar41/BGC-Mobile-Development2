package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.TrackOrderPresenter;

public class TrackOrderPresenterTest {

    ITrackOrder.ITrackOrderView view;
    ITrackOrder.ITrackOrderService service;
    ITrackOrder.ITrackOrderPresenter presenter;

    @Before
    public void setUp() {
        view = new MockTrackOrderView();
        service = new MockTrackOrderService();
        presenter = new TrackOrderPresenter(view, service);
    }

    @Test
    public void testLoadAllItems() throws InterruptedException {
        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isItemDisplayed);
    }

    @Test
    public void testDisplaySortedItems_Finished() throws InterruptedException {
        presenter.onButtonConfirmSortClicked(FINISHED);
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isItemDisplayedOnFinished);
    }

    @Test
    public void testDisplaySortedItems_Processing() throws InterruptedException {
        presenter.onButtonConfirmSortClicked(PROCESSING);
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isItemDisplayedOnProcessing);
    }

    @Test
    public void testDisplaySortedItems_Cancelled() throws InterruptedException {
        presenter.onButtonConfirmSortClicked(CANCELLED);
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isItemDisplayedOnCancelled);
    }

    @Test
    public void testDisplaySortedItems_Date() throws InterruptedException {
        presenter.onDateSortConfirmClicked(MockDatabase.ORDER_DATE.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isItemDisplayedOnDate);
    }

    @Test
    public void testDisplayShowLoader() throws InterruptedException {
        presenter.onDateSortConfirmClicked(MockDatabase.ORDER_DATE.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderShowing);

        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderShowing);

        presenter.onButtonConfirmSortClicked(CANCELLED);
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderShowing);
    }

    @Test
    public void testDisplayHideLoader() throws InterruptedException {
        presenter.onDateSortConfirmClicked(MockDatabase.ORDER_DATE.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderHiding);

        presenter.loadOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderHiding);

        presenter.onButtonConfirmSortClicked(CANCELLED);
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView) view).isLoaderHiding);
    }

    @Test
    public void testDisplaySortPopup() {
        presenter.onSortFloatinActionButtonClicked();
        Assert.assertTrue(((MockTrackOrderView) view).isPopupShowing);
    }

    @Test
    public void testDisplayNoItemsFound() throws InterruptedException {
        presenter.onButtonConfirmSortClicked("hehe");
        Thread.sleep(1000);
        Assert.assertTrue(((MockTrackOrderView)view).isDisplayEmptyPromptShowing);
    }

    @Test
    public void testHideNoItemsFound() throws InterruptedException {
        presenter.onButtonConfirmSortClicked(PROCESSING);
        Thread.sleep(1000);
        Assert.assertFalse(((MockTrackOrderView)view).isDisplayEmptyPromptShowing);

        presenter.onDateSortConfirmClicked(MockDatabase.ORDER_DATE.getVal());
        Thread.sleep(1000);
        Assert.assertFalse(((MockTrackOrderView)view).isDisplayEmptyPromptShowing);
    }

    static class MockTrackOrderView implements ITrackOrder.ITrackOrderView {
        boolean isItemDisplayed;
        boolean isItemDisplayedOnFinished;
        boolean isItemDisplayedOnProcessing;
        boolean isItemDisplayedOnCancelled;
        boolean isItemDisplayedOnDate;
        boolean isLoaderShowing;
        boolean isPopupShowing;
        boolean isDisplayEmptyPromptShowing;
        boolean isLoaderHiding;

        @Override
        public void displayProgressBar() {
            isLoaderShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isLoaderHiding = true;
        }

        @Override
        public void displayOrders(List<CustomerOrdersModel> ordersList) {
            for (CustomerOrdersModel customerOrdersModel : ordersList) {
                if (customerOrdersModel.getSpecificOrdersModelList().equals(MOCK_SPECIFIC_ORDERS) &&
                        customerOrdersModel.getCustomer_name().equals(MockDatabase.CUSTOMER_NAME.getVal()) &&
                        customerOrdersModel.getCustomer_email().equals(MockDatabase.CUSTOMER_EMAIL.getVal()) &&
                        customerOrdersModel.getOrder_price().equals(MockDatabase.ORDER_PRICE.getVal()) &&
                        customerOrdersModel.getOrder_status().equals(MockDatabase.ORDER_STATUS.getVal()) &&
                        customerOrdersModel.getOrder_date().equals(MockDatabase.ORDER_DATE.getVal())) {
                    isItemDisplayed = true;
                } else {
                    isItemDisplayed = false;
                }

                if (customerOrdersModel.getOrder_status().equals("Finished")) {
                    isItemDisplayedOnFinished = true;
                } else if (customerOrdersModel.getOrder_status().equals("Processing")) {
                    isItemDisplayedOnProcessing = true;
                } else {
                    isItemDisplayedOnCancelled = true;
                }

                if (customerOrdersModel.getOrder_date().equals(MockDatabase.ORDER_DATE.getVal())) {
                    isItemDisplayedOnDate = true;
                } else {
                    isItemDisplayedOnDate = false;
                }

            }


        }

        @Override
        public void displayPopupSortBy() {
            isPopupShowing = true;
        }

        @Override
        public void displayEmptyResult() {
            isDisplayEmptyPromptShowing = true;
        }

        @Override
        public void hideEmptyResult() {
            isDisplayEmptyPromptShowing = false;
        }
    }

    private static final String PROCESSING = "Processing";
    private static final String FINISHED = "Finished";
    private static final String CANCELLED = "Cancelled";

    enum MockDatabase {
        //Customer Orders Model
        CUSTOMER_NAME("sample"),
        CUSTOMER_EMAIL("sample@gmail.com"),
        ORDER_PRICE("100"),
        ORDER_STATUS("Processing"),
        ORDER_STATUS_F("Finished"),
        ORDER_STATUS_C("Cancelled"),
        ORDER_DATE("1/2/2021"),

        ORDER_ID("0001"),
        TOTAL_ORDERS("20"),

        //Specific Orders Model
        PRODUCT_ID("123"),
        PRODUCT_NAME("Product"),
        PRODUCT_PRICE("12");

        private String val;

        MockDatabase(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    private static final List<SpecificOrdersModel> MOCK_SPECIFIC_ORDERS = Arrays.asList(
            new SpecificOrdersModel(
                    MockDatabase.ORDER_ID.getVal(),
                    MockDatabase.PRODUCT_ID.getVal(),
                    MockDatabase.TOTAL_ORDERS.getVal(),
                    MockDatabase.PRODUCT_NAME.getVal(),
                    null,
                    MockDatabase.PRODUCT_PRICE.getVal()),
            new SpecificOrdersModel(
                    MockDatabase.ORDER_ID.getVal(),
                    MockDatabase.PRODUCT_ID.getVal(),
                    MockDatabase.TOTAL_ORDERS.getVal(),
                    MockDatabase.PRODUCT_NAME.getVal(),
                    null,
                    MockDatabase.PRODUCT_PRICE.getVal()),
            new SpecificOrdersModel(
                    MockDatabase.ORDER_ID.getVal(),
                    MockDatabase.PRODUCT_ID.getVal(),
                    MockDatabase.TOTAL_ORDERS.getVal(),
                    MockDatabase.PRODUCT_NAME.getVal(),
                    null,
                    MockDatabase.PRODUCT_PRICE.getVal())
    );

    static class MockTrackOrderService implements ITrackOrder.ITrackOrderService {

        @Override
        public List<CustomerOrdersModel> fetchOrdersFromDB() {
            return Arrays.asList(
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS
                    ),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS
                    ),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS
                    ));
        }

        @Override
        public List<CustomerOrdersModel> fetchSortedOrdersFromDB(String sort_type) {
            List<CustomerOrdersModel> ordersModel = Arrays.asList(
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS_F.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS_C.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS));
            List<CustomerOrdersModel> finalList = new ArrayList<>();
            switch (sort_type) {
                case PROCESSING:
                    for (CustomerOrdersModel model : ordersModel) {
                        if (model.getOrder_status().equals(PROCESSING)) {
                            finalList.add(model);
                        }
                    }
                    return finalList;
                case CANCELLED:
                    for (CustomerOrdersModel model : ordersModel) {
                        if (model.getOrder_status().equals(CANCELLED)) {
                            finalList.add(model);
                        }
                    }
                    return finalList;
                case FINISHED:
                    for (CustomerOrdersModel model : ordersModel) {
                        if (model.getOrder_status().equals(FINISHED)) {
                            finalList.add(model);
                        }
                    }
                    return finalList;
                default:
                    return Collections.emptyList();
            }
        }

        @Override
        public List<CustomerOrdersModel> fetchOrderByDate(String dateString) {
            return Arrays.asList(
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS_F.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS),
                    new CustomerOrdersModel(
                            MockDatabase.ORDER_ID.getVal(),
                            MockDatabase.CUSTOMER_NAME.getVal(),
                            MockDatabase.CUSTOMER_EMAIL.getVal(),
                            MockDatabase.ORDER_PRICE.getVal(),
                            MockDatabase.ORDER_STATUS_C.getVal(),
                            MockDatabase.ORDER_DATE.getVal(),
                            MockDatabase.TOTAL_ORDERS.getVal(),
                            MOCK_SPECIFIC_ORDERS));
        }
    }

}
