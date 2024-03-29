package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.CheckoutService;
import emp.project.softwareengineeringprojectcustomer.Presenter.CheckoutPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.CheckoutRecyclerView;

public class CheckoutActivityView extends AppCompatActivity implements ICheckout.ICheckoutView {
    private RecyclerView recyclerViewOrders;
    private TextView txtCartTotal;
    private Button btnCheckout;
    private LottieAnimationView lottieAnimationViewLoader, lottieAnimationView_emptyCart;
    private ICheckout.ICheckoutPresenter presenter;

    private View instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_checkout_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        recyclerViewOrders = findViewById(R.id.recyclerView_Cart);
        txtCartTotal = findViewById(R.id.txt_total_value);
        btnCheckout = findViewById(R.id.btn_checkout);
        lottieAnimationViewLoader = findViewById(R.id.progressBar);
        btnCheckout = findViewById(R.id.btn_checkout);
        lottieAnimationView_emptyCart = findViewById(R.id.empty_cart);

        presenter = new CheckoutPresenter(this, CheckoutService.getInstance());
        presenter.loadOrders();
        presenter.loadCartTotals();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance = v;
                presenter.onCheckoutButtonClicked();
            }
        });

    }

    @Override
    public void displayCartOrders() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(CheckoutActivityView.this, LinearLayoutManager.VERTICAL, false);
                CheckoutRecyclerView adapter = new CheckoutRecyclerView(CheckoutActivityView.this, CartModel.getInstance().getCartValues(), presenter);
                recyclerViewOrders.setLayoutManager(layoutManager);
                recyclerViewOrders.setAdapter(adapter);
                recyclerViewOrders.scheduleLayoutAnimation();
            }
        });
    }

    @Override
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationViewLoader.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationViewLoader.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void displaySuccessfullPrompt() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckoutActivityView.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_successful_checkout, null);
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void displayCartValues() {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                txtCartTotal.setText("₱" + CartModel.getInstance().calculateTotalOrderValues());
            }
        });
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snack = Snackbar.make(instance, errorMessage, Snackbar.LENGTH_LONG);
                View view = snack.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(CheckoutActivityView.this, android.R.color.holo_orange_dark));
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error_24, 0, 0, 0);
                tv.setGravity(Gravity.CENTER);
                snack.show();
            }
        });
    }

    @Override
    public void displayEmptyCart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_emptyCart.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideEmptyCart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_emptyCart.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}