package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

public interface ILogin {
    interface ILoginView {
        void onSuccess();

        void onError(String errorMessage);

        void displayProgressLoader();

        void hideProgressLoader();

        void setErrorUsername();

        void setErrorPassword();

        void removeErrorUsername();

        void removeErrorPassword();
    }

    interface ILoginPresenter {
        void onLoginButtonClicked(String username, String password);
    }

    interface ILoginService extends IStrictMode {

        boolean fetchCustomerLoginCredentials(String username, String password) throws SQLException, ClassNotFoundException;
    }
}
