package emp.project.softwareengineeringprojectcustomer.Presenter;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;

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
                view.displayRecyclerViewValues(service.fetchInformationData());
                view.hideProgressBar();
            }
        });thread.start();
    }
}
