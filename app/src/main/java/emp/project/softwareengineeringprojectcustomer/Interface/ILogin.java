package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;

public interface ILogin {
    interface ILoginView {
        void onSuccess();

        void displaySnackBarMessage(String errorMessage);

        void displayProgressLoader();

        void hideProgressLoader();

        Boolean displayErrors();

        void displayPopupConfirmation();

        void displayStatusMessage(String message);
    }

    interface ILoginPresenter {
        void onLoginButtonClicked(String username, String password);

        void onSubmitCodeButtonClicked(String code,String username);
    }

    interface ILoginService extends IStrictMode {
        void updateUserStatus(String username) throws ClassNotFoundException, SQLException;

        enum LoginStatus {
            ACTIVE,
            PENDING,
            NOT_FOUND
        }

        LoginStatus fetchCustomerLoginCredentials(String username, String password) throws SQLException, ClassNotFoundException;

        Boolean validateCode(String code,String username) throws ClassNotFoundException, SQLException;
    }
}
