package emp.project.softwareengineeringprojectcustomer.Presenter;

import com.google.android.material.textfield.TextInputLayout;

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
    public void onUpdateProfileButtonClicked(InputStream profilePicture, TextInputLayout[] arrTexts) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBarPopup();
                if(view.displayErrors()){
                    CustomerModel userModel = new CustomerModel(
                            arrTexts[0].getEditText().getText().toString(),
                            arrTexts[1].getEditText().getText().toString(),
                            arrTexts[2].getEditText().getText().toString(),
                            arrTexts[3].getEditText().getText().toString(),
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
