package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import android.util.Patterns;

import java.io.InputStream;
import java.sql.Blob;
import java.util.HashSet;

public class CustomerModel {
    private String user_id, user_username, user_password, user_fullname, user_status, user_email;
    private InputStream inputStream;
    private Blob picture;

    public CustomerModel(String user_username, String user_password, String user_fullname, String user_status, String user_email, InputStream inputStream) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
        this.user_email = user_email;
        this.inputStream = inputStream;
    }

    //This constructor is for updating user credentials
    public CustomerModel(String user_username, String user_password, String user_fullname, String user_email, InputStream inputStream) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_email = user_email;
        this.inputStream = inputStream;
    }

    //this constructor is for the userprofile fragment display
    public CustomerModel(String user_id, String user_username, String user_password, String user_fullname, String user_status, String user_email, Blob picture) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
        this.user_email = user_email;
        this.picture = picture;
    }


    //This contructor is for the display of user credentials display on the side navigation bar
    public CustomerModel(String user_username, String user_fullname, String user_email, Blob picture) {
        this.user_username = user_username;
        this.user_fullname = user_fullname;
        this.user_email = user_email;
        this.picture = picture;
    }

    public CustomerModel() {
    }

    public Blob getPicture() {
        return picture;
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

    public HashSet<VALIDITY> validateLogin(String username, String password) {
        HashSet<VALIDITY> validityList = new HashSet<>();
        if (username.isEmpty()) {
            validityList.add(VALIDITY.EMPTY_FIELD_USERNAME);
        } else {
            validityList.add(VALIDITY.VALID_FIELD_USERNAME);
        }
        if (password.isEmpty()) {
            validityList.add(VALIDITY.EMPTY_PASSWORD);
        } else {
            validityList.add(VALIDITY.VALID_PASSWORD);
        }
        if (!username.isEmpty() && !password.isEmpty()) {
            validityList.add(VALIDITY.VALID);
        }
        return validityList;

    }

    public enum VALIDITY {
        EMPTY_FIELD,
        EMPTY_FIELD_USERNAME,
        EMPTY_EMAIL,
        EMPTY_FULLNAME,
        EMPTY_PASSWORD,//for login
        NOT_VALID_EMAIL_PATTERN,

        VALID_FIELD_USERNAME,
        VALID_EMAIL,
        VALID_FULLNAME,
        VALID_PASSWORD,//for login
        VALID_EMAIL_PATTERN,

        VALID;
    }

    public HashSet<VALIDITY> validateRegistration(String[] arrTexts, InputStream FILE_INPUT_STREAM) {
        HashSet<VALIDITY> set = new HashSet<>();
        // This if for the update profile
        for (int i = 0; i < arrTexts.length; i++) {
            if (arrTexts[i].trim().isEmpty()) {
                switch (i) {
                    case 0:
                        set.add(VALIDITY.EMPTY_FIELD_USERNAME);
                        break;
                    case 1:
                        set.add(VALIDITY.EMPTY_PASSWORD);
                        break;
                    case 2:
                        set.add(VALIDITY.EMPTY_EMAIL);
                        break;
                    case 3:
                        set.add(VALIDITY.EMPTY_FULLNAME);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        set.add(VALIDITY.VALID_FIELD_USERNAME);
                        break;
                    case 1:
                        set.add(VALIDITY.VALID_PASSWORD);
                        break;
                    case 2:
                        set.add(VALIDITY.VALID_EMAIL);
                        break;
                    case 3:
                        set.add(VALIDITY.VALID_FULLNAME);
                        break;
                }
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(arrTexts[3]).matches()) {
            set.add(VALIDITY.NOT_VALID_EMAIL_PATTERN);
        } else {
            set.add(VALIDITY.VALID_EMAIL_PATTERN);
        }

        if (set.contains(VALIDITY.VALID_EMAIL) &&
                set.contains(VALIDITY.VALID_PASSWORD) &&
                set.contains(VALIDITY.VALID_FIELD_USERNAME) &&
                set.contains(VALIDITY.VALID_FULLNAME) &&
                set.contains(VALIDITY.VALID_EMAIL_PATTERN)) {
            set.clear();
            set.add(VALIDITY.VALID);
        }
        return set;
    }
}
