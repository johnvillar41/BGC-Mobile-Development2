package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IMain {
    interface IMainView {
        void displayUserDetails(CustomerModel userList);

        void displayTotalNumberOfCartNumbers();
    }

    interface IMainPresenter {
        void loadUserDetails();

        void loadCartNumber();
    }

    interface IMainService extends IStrictMode {
        CustomerModel fetchUserDetails() throws ClassNotFoundException, SQLException;
    }
}
