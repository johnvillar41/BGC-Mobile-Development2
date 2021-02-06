package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import android.util.Patterns;

import java.io.InputStream;
import java.sql.Blob;
import java.util.HashSet;

public class CustomerModel {
    private String user_id, user_username, user_password, user_fullname, user_status, user_email;
    private InputStream inputStream;
    private Blob picture;
    private String code;
    //This constructor is for registering new users
    public CustomerModel(String user_username, String user_password, String user_fullname, String user_status, String user_email, InputStream inputStream,String code) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_fullname = user_fullname;
        this.user_status = user_status;
        this.user_email = user_email;
        this.inputStream = inputStream;
        this.code = code;
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

    public String getCode() {
        return code;
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
}
