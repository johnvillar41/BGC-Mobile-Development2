package emp.project.softwareengineeringprojectcustomer.Interface;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;

public interface IRegister {
    interface IRegisterView {
        void onSuccess();

        void onError(String errorMessage);

        void displayLoadingCircle();

        void hideLoadingCircler();

        void loadImageFromGallery();

        void setErrorUsername();

        void setErrorPassword_1();

        void setErrorPassword_2();

        void setErrorEmail();

        void setErrorFullname();

        void removeErrorUsername();

        void removeErrorPassword_1();

        void removeErrorPassword_2();

        void removeErrorEmail();

        void removeErrorFullname();

        void setErrorOnNotEqualPassword();

        void removeErrorEqualPassword();
    }

    interface IRegisterPresenter {
        void onRegisterButtonClicked(String username, String password_1, String password_2, String fullname,String email, InputStream FILE_INPUT_STREAM);


        void onImageButtonClicked();
    }

    interface IRegisterService extends IStrictMode {
        void insertCustomerToDB(CustomerModel model) throws ClassNotFoundException, SQLException;
    }
}
