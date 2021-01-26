package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IMain {
    interface IMainView {
        void displayUserDetails(CustomerModel userList);

        void displayLoadingScreen();

        void hideLoadingScreen();
    }

    interface IMainPresenter {
        void loadUserDetails();
    }

    interface IMainService extends IStrictMode {
        CustomerModel fetchUserDetails() throws ClassNotFoundException, SQLException;
    }
}
