package emp.project.softwareengineeringprojectcustomer.Interface;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IUser {
    interface IUserView extends IBaseView{
        void displayUserCredentials(CustomerModel userModel);

        void displayUpdatePopup();

        void loadImageFromGallery();

        void displayUserCredentialsPopup(CustomerModel userModel);

        void displayProgressBarPopup();

        void hideProgressBarPopup();

        void logout();

        Boolean displayErrors();

        void displayMessage(String message);
    }

    interface IUserPresenter {
        void onFloatingUpdateButtonClicked();

        void loadCredentials();

        void onSelectImageButtonClicked();

        void onUpdateProfileButtonClicked(InputStream profilePicture, String[]arrTexts);

        void loadPopupValues();
    }

    interface IUserService extends IStrictMode{
        CustomerModel fetchUserCredentials() throws ClassNotFoundException, SQLException;

        void updateUserCredentials(CustomerModel userModel) throws ClassNotFoundException, SQLException;
    }
}
