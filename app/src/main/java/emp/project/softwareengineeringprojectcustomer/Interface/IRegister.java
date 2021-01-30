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

        Boolean displayErrors();
    }

    interface IRegisterPresenter {
        void onRegisterButtonClicked(List<String> arrTexts, InputStream FILEINPUTSTREAM);


        void onImageButtonClicked();
    }

    interface IRegisterService extends IStrictMode {
        void insertCustomerToDB(CustomerModel model) throws ClassNotFoundException, SQLException;
    }
}
