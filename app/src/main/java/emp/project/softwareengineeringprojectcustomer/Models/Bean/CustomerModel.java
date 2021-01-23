package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CustomerModel {
    private String user_id, user_username, user_password, user_fullname, user_status, user_email;
    private InputStream inputStream;

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

    public enum VALIDITY {
        EMPTY_FIELD,
        EMPTY_FIELD_USERNAME,
        EMPTY_FIELD_PASSWORD_1,
        EMPTY_FIELD_PASSWORD_2,
        EMPTY_EMAIL,
        EMPTY_FULLNAME,
        EMPTY_IMAGE,

        EQUAL_PASSWORD,
        NOT_EQUAL_PASSWORD,

        VALID_FIELD_USERNAME,
        VALID_FIELD_PASSWORD_1,
        VALID_FIELD_PASSWORD_2,
        VALID_EMAIL,
        VALID_FULLNAME,
        VALID_IMAGE,

        PASSWORD_NOT_EQUAL,
        VALID;
    }

    public HashSet<VALIDITY> validateRegistration(String[] arrTexts, InputStream FILE_INPUT_STREAM) {
        HashSet<VALIDITY> set = new HashSet<>();
        for (int i = 0; i < arrTexts.length; i++) {
            if(arrTexts[i].isEmpty()) {
                switch (i) {
                    case 0:
                        set.add(VALIDITY.EMPTY_FIELD_USERNAME);
                        break;
                    case 1:
                        set.add(VALIDITY.EMPTY_FIELD_PASSWORD_1);
                        break;
                    case 2:
                        set.add(VALIDITY.EMPTY_FIELD_PASSWORD_2);
                        break;
                    case 3:
                        set.add(VALIDITY.EMPTY_EMAIL);
                        break;
                    case 4:
                        set.add(VALIDITY.EMPTY_FULLNAME);
                        break;
                }
            } else {
                switch (i){
                    case 0:
                        set.add(VALIDITY.VALID_FIELD_USERNAME);
                        break;
                    case 1:
                        set.add(VALIDITY.VALID_FIELD_PASSWORD_1);
                        break;
                    case 2:
                        set.add(VALIDITY.VALID_FIELD_PASSWORD_2);
                        break;
                    case 3:
                        set.add(VALIDITY.VALID_EMAIL);
                        break;
                    case 4:
                        set.add(VALIDITY.VALID_FULLNAME);
                        break;
                }
            }
        }

        if (FILE_INPUT_STREAM == null) {
            set.add(VALIDITY.EMPTY_IMAGE);
        } else {
            set.add(VALIDITY.VALID_IMAGE);
        }

        if(arrTexts[1].equals(arrTexts[2]) && !arrTexts[1].isEmpty()){
            set.add(VALIDITY.EQUAL_PASSWORD);
        } else {
            set.add(VALIDITY.NOT_EQUAL_PASSWORD);
        }

        if(set.contains(VALIDITY.VALID_EMAIL) &&
                set.contains(VALIDITY.VALID_FIELD_PASSWORD_1) &&
                set.contains(VALIDITY.VALID_FIELD_PASSWORD_2) &&
                set.contains(VALIDITY.VALID_FIELD_USERNAME) &&
                set.contains(VALIDITY.VALID_IMAGE) &&
                set.contains(VALIDITY.VALID_FULLNAME) &&
                set.contains(VALIDITY.EQUAL_PASSWORD)){
            set.clear();
            set.add(VALIDITY.VALID);
        }

        return set;
    }

    /**TODO: 1)tatangalin yung gif sa registration form
     *       2)maglagay ng label sa ilalim ng add photo picture("add profile picture")
     *       3)gawing fit sa loob ng circle yung logo
     *       4)Product recycler view: rename Name to Product name:     *
     *       5)product popup: baguhin ang edittext into +_-
     *       6)checkout recyclerview: maglagay ng cost
     *       7)Gawing transparent and box sa checkout successful prompt
     */

}
