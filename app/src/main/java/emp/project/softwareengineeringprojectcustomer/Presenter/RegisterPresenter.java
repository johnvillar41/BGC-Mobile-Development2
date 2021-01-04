package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Service.RegisterService;
import emp.project.softwareengineeringprojectcustomer.Views.RegisterActivityView;

public class RegisterPresenter implements IRegister.IRegisterPresenter {

    private IRegister.IRegisterView view;
    private IRegister.IRegisterService service;
    private CustomerModel model;
    private WeakReference<RegisterActivityView> weakReference;

    public RegisterPresenter(IRegister.IRegisterView view, CustomerModel model, RegisterActivityView weakReference) {
        this.view = view;
        this.model = model;
        this.weakReference = new WeakReference<>(weakReference);
        this.service = RegisterService.getInstance();
    }

    @Override
    public void onRegisterButtonClicked(String username, String password_1, String password_2, String fullname) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String errorMessage = model.validateRegistration(username, password_1, password_2);
                    if (model.validateRegistration(username, password_1, password_2).equals(CustomerModel.VALID)) {
                        model = new CustomerModel(username, CustomerModel.FINAL_PASSWORD, fullname, CustomerModel.CUSTOMER_STATUS_ACTIVE);
                        service.insertCustomerToDB(model);
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onSuccess();
                            }
                        });
                    } else {
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError(errorMessage);
                            }
                        });
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    weakReference.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onError(e.getMessage());
                        }
                    });
                    e.printStackTrace();

                }
            }
        });
        thread.start();
    }
}
