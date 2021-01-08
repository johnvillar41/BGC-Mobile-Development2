package emp.project.softwareengineeringprojectcustomer.Presenter;

import android.app.Activity;
import android.content.Context;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.RegisterService;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.RegisterActivityView;

public class RegisterPresenter implements IRegister.IRegisterPresenter {

    private IRegister.IRegisterView view;
    private IRegister.IRegisterService service;
    private CustomerModel model;
    private WeakReference<Context> weakReference;

    public RegisterPresenter(IRegister.IRegisterView view, CustomerModel model, Context weakReference) {
        this.view = view;
        this.model = model;
        this.weakReference = new WeakReference<>(weakReference);
        this.service = RegisterService.getInstance();
    }

    public static final String ENTER_ALL_FIELDS = "Please enter all fields!";
    public static final String PASSWORD_NOT_EQUAL = "Password fields are not equal!";
    public static final String EMPTY_IMAGE = "Please Enter Image!";
    public static final String CUSTOMER_STATUS_PENDING = "Pending";

    @Override
    public void onRegisterButtonClicked(String username, String password_1, String password_2, String fullname, String email, InputStream FILE_INPUT_STREAM) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayLoadingCircle();
                    }
                });
                try {
                    CustomerModel.VALIDITY validity = model.validateRegistration(username, password_1, password_2, email, FILE_INPUT_STREAM);
                    switch (validity) {
                        case VALID:
                            model = new CustomerModel(username, CustomerModel.FINAL_PASSWORD, fullname, CUSTOMER_STATUS_PENDING, email, FILE_INPUT_STREAM);
                            service.insertCustomerToDB(model);
                            ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onSuccess();
                                    view.hideLoadingCircler();
                                }
                            });
                            break;
                        case EMPTY_FIELD:
                            ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(ENTER_ALL_FIELDS);
                                    view.hideLoadingCircler();
                                }
                            });
                            break;
                        case PASSWORD_NOT_EQUAL:
                            ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(PASSWORD_NOT_EQUAL);
                                    view.hideLoadingCircler();
                                }
                            });
                            break;
                        case EMPTY_IMAGE:
                            ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(EMPTY_IMAGE);
                                    view.hideLoadingCircler();
                                }
                            });
                            break;
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    ((Activity)weakReference.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onError(e.getMessage());
                            view.hideLoadingCircler();
                        }
                    });
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
