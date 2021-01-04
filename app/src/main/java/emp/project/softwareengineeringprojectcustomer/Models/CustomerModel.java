package emp.project.softwareengineeringprojectcustomer.Models;

public class CustomerModel {
    private String user_id, user_username, user_password, user_fullname, user_status;

    public CustomerModel(String user_id, String user_username, String user_password, String user_fullname, String user_status) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
    }

    public CustomerModel(String user_username, String user_password, String user_fullname, String user_status) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
    }

    public CustomerModel() {
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public String getUser_status() {
        return user_status;
    }

    public boolean validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private static final String EMPTY_FIELD = "One or more fields are empty!";
    private static final String PASSWORD_NOT_EQUAL = "Password fields not equal!";
    public static final String VALID = "Successfull!";
    public static String FINAL_PASSWORD = null;
    public static final String CUSTOMER_STATUS_ACTIVE = "Active";

    public String validateRegistration(String username, String password_1, String password_2) {
        if (username.isEmpty() || password_1.isEmpty() || password_2.isEmpty()) {
            return EMPTY_FIELD;
        } else if (!password_1.equals(password_2)) {
            return PASSWORD_NOT_EQUAL;
        } else {
            FINAL_PASSWORD = password_1;
            return VALID;
        }
    }
}
