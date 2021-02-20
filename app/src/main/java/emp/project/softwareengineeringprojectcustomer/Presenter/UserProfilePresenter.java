package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.io.InputStream;
import java.sql.SQLException;

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
                    view.displayProgressBar();
                    CustomerModel userModel = service.fetchUserCredentials();
                    view.displayUserCredentials(userModel);
                    view.hideProgressBar();
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
                if (view.displayErrors()) {
                    CustomerModel userModel = new CustomerModel(
                            arrTexts[0],
                            arrTexts[1],
                            arrTexts[2],
                            arrTexts[3],
                            profilePicture
                    );
                    boolean shouldLogout = true;
                    try {
                        service.updateUserCredentials(userModel);
                    } catch (ClassNotFoundException e) {
                        view.displayMessage(e.getMessage());
                        shouldLogout = false;
                    } catch (SQLException throwables) {
                        view.displayMessage(throwables.getMessage());
                        shouldLogout = false;
                    }
                    if (shouldLogout) {
                        view.logout();
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
