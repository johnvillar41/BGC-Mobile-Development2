package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IRegister {
    interface IRegisterView {
        void onSuccess();

        void onError(String errorMessage);
    }

    interface IRegisterPresenter {
        void onRegisterButtonClicked(String username, String password_1, String password_2, String fullname);
    }

    interface IRegisterService extends IStrictMode {
        void insertCustomerToDB(CustomerModel model) throws ClassNotFoundException, SQLException;
    }
}
