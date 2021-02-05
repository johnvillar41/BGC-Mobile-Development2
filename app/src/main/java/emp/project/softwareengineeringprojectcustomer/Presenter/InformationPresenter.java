package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;

public class InformationPresenter implements IInformation.IInformationPresenter {

    IInformation.IInformationView view;
    IInformation.IInformationService service;

    public InformationPresenter(IInformation.IInformationView view, IInformation.IInformationService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadPageValues() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    List<InformationModel> informationModelList = service.fetchInformationData();
                    view.displayRecyclerViewValues(informationModelList);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                view.hideProgressBar();
            }
        });thread.start();
    }
}
