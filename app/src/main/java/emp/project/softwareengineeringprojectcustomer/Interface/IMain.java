package emp.project.softwareengineeringprojectcustomer.Interface;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.MainActivityView;

public interface IMain {
    interface IMainView {
        void displayUserDetails(CustomerModel userList);
    }

    interface IMainPresenter {
        void loadUserDetails();

        void loadCartNumber();
    }

    interface IMainService extends IStrictMode {
        CustomerModel fetchUserDetails() throws ClassNotFoundException, SQLException;
    }
}
