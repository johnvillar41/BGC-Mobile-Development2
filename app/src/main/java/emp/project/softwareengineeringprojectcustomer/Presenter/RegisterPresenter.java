package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.RegisterActivityView;

public class RegisterPresenter implements IRegister.IRegisterPresenter {

    private IRegister.IRegisterView view;
    private IRegister.IRegisterService service;
    private CustomerModel model;

    public RegisterPresenter(IRegister.IRegisterView view, CustomerModel model, IRegister.IRegisterService service) {
        this.view = view;
        this.model = model;
        this.service = service;
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
                view.displayLoadingCircle();
                try {
                    String[] arrTexts = new String[5];
                    arrTexts[0] = username;
                    arrTexts[1] = password_1;
                    arrTexts[2] = password_2;
                    arrTexts[3] = email;
                    arrTexts[4] = fullname;
                    HashSet<CustomerModel.VALIDITY> validity = model.validateRegistration(arrTexts, FILE_INPUT_STREAM);
                    for (CustomerModel.VALIDITY valid : validity) {
                        switch (valid) {
                            case VALID:
                                model = new CustomerModel(username, arrTexts[1], fullname, CUSTOMER_STATUS_PENDING, email, FILE_INPUT_STREAM);
                                service.insertCustomerToDB(model);
                                view.onSuccess();
                                view.removeErrorUsername();
                                view.removeErrorPassword_1();
                                view.removeErrorPassword_2();
                                view.removeErrorEmail();
                                view.hideLoadingCircler();
                                break;
                            case EMPTY_FIELD_USERNAME:
                                view.setErrorUsername();
                                view.hideLoadingCircler();
                                view.onError(ENTER_ALL_FIELDS);
                                break;
                            case EMPTY_FIELD_PASSWORD_1:
                                view.setErrorPassword_1();
                                view.hideLoadingCircler();
                                view.onError(ENTER_ALL_FIELDS);
                                break;
                            case EMPTY_FIELD_PASSWORD_2:
                                view.setErrorPassword_2();
                                view.hideLoadingCircler();
                                view.onError(ENTER_ALL_FIELDS);
                                break;
                            case EMPTY_EMAIL:
                                view.setErrorEmail(RegisterActivityView.ERROR_MESSAGE.EMAIL.getVal());
                                view.hideLoadingCircler();
                                view.onError(ENTER_ALL_FIELDS);
                                break;
                            case PASSWORD_NOT_EQUAL:
                                view.onError(PASSWORD_NOT_EQUAL);
                                view.setErrorPassword_2();
                                view.setErrorPassword_1();
                                view.hideLoadingCircler();
                                break;
                            case EMPTY_IMAGE:
                                view.onError(EMPTY_IMAGE);
                                view.hideLoadingCircler();
                                break;
                            case EMPTY_FULLNAME:
                                view.setErrorFullname();
                                view.hideLoadingCircler();
                                break;
                            case NOT_VALID_EMAIL_PATTERN:
                                view.setErrorEmail(RegisterActivityView.ERROR_MESSAGE.EMAIL_NOT_VALID.getVal());
                                view.hideLoadingCircler();
                                break;
                            case NOT_EQUAL_PASSWORD:
                                view.setErrorOnNotEqualPassword();
                                view.hideLoadingCircler();
                                break;

                            //CASES FOR VALIDS
                            case VALID_FIELD_USERNAME:
                                view.removeErrorUsername();
                                view.hideLoadingCircler();
                                break;
                            case VALID_FIELD_PASSWORD_1:
                                view.removeErrorPassword_1();
                                view.hideLoadingCircler();
                                break;
                            case VALID_FIELD_PASSWORD_2:
                                view.removeErrorPassword_2();
                                view.hideLoadingCircler();
                                break;
                            case VALID_EMAIL:
                            case VALID_EMAIL_PATTERN:
                                if (!validity.contains(CustomerModel.VALIDITY.NOT_VALID_EMAIL_PATTERN) || validity.contains(CustomerModel.VALIDITY.EMPTY_EMAIL)) {
                                    view.removeErrorEmail();
                                    view.hideLoadingCircler();
                                }
                                break;
                            case VALID_IMAGE:
                                view.hideLoadingCircler();
                                break;
                            case VALID_FULLNAME:
                                view.hideLoadingCircler();
                                view.removeErrorFullname();
                                break;
                            case EQUAL_PASSWORD:
                                view.removeErrorEqualPassword();
                                view.hideLoadingCircler();
                                break;
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    view.onError(e.getMessage());
                    view.hideLoadingCircler();
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
