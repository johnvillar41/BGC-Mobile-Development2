package emp.project.softwareengineeringprojectcustomer.Interface;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.MainActivityView;

public interface ICart {
    default void displayTotalCartNumbers(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivityView.txt_total_cart.setVisibility(View.VISIBLE);
                MainActivityView.txt_total_cart.setText(CartModel.getInstance().getTotalNumberOfOrders());
            }
        });
    }

    default void hideTotalCartNumbers(Context context) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivityView.txt_total_cart.setVisibility(View.INVISIBLE);
            }
        });
    }

    default void destroyCartNumber() {
        MainActivityView.txt_total_cart = null;
    }
}
