package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Service.RegisterService;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.RegisterActivityView;

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

    private static final String ENTER_ALL_FIELDS = "Please enter all fields!";
    private static final String PASSWORD_NOT_EQUAL = "Password fields are not equal!";
    private static final String EMPTY_IMAGE = "Please Enter Image!";

    @Override
    public void onRegisterButtonClicked(String username, String password_1, String password_2, String fullname, String email, InputStream FILE_INPUT_STREAM) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                weakReference.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayLoadingCircle();
                    }
                });
                try {
                    CustomerModel.VALIDITY validity = model.validateRegistration(username, password_1, password_2, email, FILE_INPUT_STREAM);
                    if (validity.equals(CustomerModel.VALIDITY.VALID)) {
                        model = new CustomerModel(username, CustomerModel.FINAL_PASSWORD, fullname, CustomerModel.CUSTOMER_STATUS_PENDING, email, FILE_INPUT_STREAM);
                        service.insertCustomerToDB(model);
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onSuccess();
                                view.hideLoadingCircler();
                            }
                        });
                    } else if (validity.equals(CustomerModel.VALIDITY.EMPTY_FIELD)) {
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError(ENTER_ALL_FIELDS);
                                view.hideLoadingCircler();
                            }
                        });
                    } else if (validity.equals(CustomerModel.VALIDITY.PASSWORD_NOT_EQUAL)) {
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError(PASSWORD_NOT_EQUAL);
                                view.hideLoadingCircler();
                            }
                        });
                    } else if (validity.equals(CustomerModel.VALIDITY.EMPTY_IMAGE)) {
                        weakReference.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError(EMPTY_IMAGE);
                                view.hideLoadingCircler();
                            }
                        });
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    weakReference.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onError(e.getMessage());
                            view.hideLoadingCircler();
                        }
                    });
                    e.printStackTrace();

                }
            }
        });
        thread.start();
    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }
}
