package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IUser {
    interface IUserView {
        void displayLoader();

        void hideLoader();

        void displayUserCredentials(CustomerModel userModel);

        void displayUpdatePopup();
    }

    interface IUserPresenter {
        void onFloatingUpdateButtonClicked();

        void loadCredentials();
    }

    interface IUserService extends IStrictMode{
        CustomerModel fetchUserCredentials() throws ClassNotFoundException, SQLException;
    }
}
