package emp.project.softwareengineeringprojectcustomer.Interface;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IUser {
    interface IUserView {
        void displayLoader();

        void hideLoader();

        void displayUserCredentials(CustomerModel userModel);

        void displayUpdatePopup();

        void loadImageFromGallery();

        void displayUserCredentialsPopup(CustomerModel userModel);

        void displayProgressBarPopup();

        void hideProgressBarPopup();

        void logout();

        void setErrorUsername();

        void setErrorPassword();

        void setErrorEmail(String errorMessage);

        void setErrorFullname();

        void removeErrorUsername();

        void removeErrorPassword();

        void removeErrorEmail();

        void removeErrorFullname();
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
