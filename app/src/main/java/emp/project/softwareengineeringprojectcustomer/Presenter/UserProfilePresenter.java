package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;

import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public class UserProfilePresenter implements IUser.IUserPresenter {

    private IUser.IUserView view;
    private IUser.IUserService service;

    public UserProfilePresenter(IUser.IUserView view, IUser.IUserService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onFloatingUpdateButtonClicked() {
        view.displayUpdatePopup();
    }

    @Override
    public void loadCredentials() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayLoader();
                    CustomerModel userModel = service.fetchUserCredentials();
                    view.displayUserCredentials(userModel);
                    view.hideLoader();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onSelectImageButtonClicked() {
        view.loadImageFromGallery();
    }

    @Override
    public void onUpdateProfileButtonClicked(InputStream profilePicture, String[] arrTexts) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBarPopup();
                CustomerModel validateModel = new CustomerModel();
                HashSet<CustomerModel.VALIDITY> validities = validateModel.validateRegistration(arrTexts, profilePicture);
                for (CustomerModel.VALIDITY validity : validities) {
                    switch (validity) {
                        case EMPTY_FIELD_USERNAME:
                            view.setErrorUsername();
                            break;
                        case EMPTY_PASSWORD:
                            view.setErrorPassword();
                            break;
                        case EMPTY_FULLNAME:
                            view.setErrorFullname();
                            break;
                        case EMPTY_EMAIL:
                            view.setErrorEmail("Empty Email");
                            break;
                        case NOT_VALID_EMAIL_PATTERN:
                            view.setErrorEmail("Email pattern not valid!");
                            break;

                        case VALID_FIELD_USERNAME:
                            view.removeErrorUsername();
                            break;
                        case VALID_PASSWORD:
                            view.removeErrorPassword();
                            break;
                        case VALID_FULLNAME:
                            view.removeErrorFullname();
                            break;
                        case VALID_EMAIL:
                        case VALID_EMAIL_PATTERN:
                            view.removeErrorEmail();
                            break;

                        case VALID:
                            CustomerModel userModel = new CustomerModel(
                                    arrTexts[0],
                                    arrTexts[1],
                                    arrTexts[2],
                                    arrTexts[3],
                                    profilePicture
                            );
                            try {
                                service.updateUserCredentials(userModel);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            view.logout();
                            break;

                    }
                }
                view.hideProgressBarPopup();

            }
        });
        thread.start();
    }

    @Override
    public void loadPopupValues() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.displayProgressBarPopup();
                    CustomerModel userModel = service.fetchUserCredentials();
                    view.displayUserCredentialsPopup(userModel);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                view.hideProgressBarPopup();
            }
        });
        thread.start();
    }
}
