package emp.project.softwareengineeringprojectcustomer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.InformationPresenter;

public class InformationPresenterTest {
    IInformation.IInformationView view;
    IInformation.IInformationService service;
    IInformation.IInformationPresenter presenter;

    @Before
    public void setUp() {
        view = new MockInformationView();
        service = new MockInformationService();
        presenter = new InformationPresenter(view, service);
    }

    @Test
    public void testDisplayInformation() throws InterruptedException {
        presenter.loadPageValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockInformationView) view).isDataShowing);
    }

    static class MockInformationView implements IInformation.IInformationView {
        boolean isDataShowing;

        @Override
        public void displayProgressBar() {

        }

        @Override
        public void hideProgressBar() {

        }

        @Override
        public void displayRecyclerViewValues(List<InformationModel> informationModelList) {
            if (informationModelList.size() == 4) {
                isDataShowing = true;
            }
        }
    }

    static class MockInformationService implements IInformation.IInformationService {

        @Override
        public List<InformationModel> fetchInformationData() {
            return Arrays.asList(
                    new InformationModel("1", new ProductModel()),
                    new InformationModel("1", new ProductModel()),
                    new InformationModel("1", new ProductModel()),
                    new InformationModel("1", new ProductModel())
            );
        }
    }
}
