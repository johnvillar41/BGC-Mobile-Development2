package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.io.InputStream;

public class CustomerModel {
    private String user_id, user_username, user_password, user_fullname, user_status, user_email;
    private InputStream inputStream;

    public CustomerModel(String user_id, String user_username, String user_password, String user_fullname, String user_status, String user_email, InputStream inputStream) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
        this.user_email = user_email;
        this.inputStream = inputStream;
    }

    public CustomerModel(String user_username, String user_password, String user_fullname, String user_status, String user_email, InputStream inputStream) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
        this.user_email = user_email;
        this.inputStream = inputStream;
    }

    public CustomerModel() {
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getUser_email() {
        return user_email;
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

    public VALIDITY validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return VALIDITY.EMPTY_FIELD;
        } else {
            return VALIDITY.VALID;
        }
    }

    public static String FINAL_PASSWORD = null;
    public static final String CUSTOMER_STATUS_PENDING = "Pending";

    public enum VALIDITY {
        EMPTY_FIELD,
        EMPTY_IMAGE,
        PASSWORD_NOT_EQUAL,
        VALID;
    }

    public VALIDITY validateRegistration(String username, String password_1, String password_2, String user_email, InputStream FILE_INPUT_STREAM) {
        if (username.isEmpty() || password_1.isEmpty() || password_2.isEmpty() || user_email.isEmpty()) {
            return VALIDITY.EMPTY_FIELD;
        } else if (!password_1.equals(password_2)) {
            return VALIDITY.PASSWORD_NOT_EQUAL;
        } else if (FILE_INPUT_STREAM == null) {
            return VALIDITY.EMPTY_IMAGE;
        } else {
            FINAL_PASSWORD = password_1;
            return VALIDITY.VALID;
        }
    }
}
