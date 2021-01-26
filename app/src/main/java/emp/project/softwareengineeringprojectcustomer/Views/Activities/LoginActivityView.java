package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.LoginService;
import emp.project.softwareengineeringprojectcustomer.Presenter.LoginPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

public class LoginActivityView extends AppCompatActivity implements ILogin.ILoginView {
    private ILogin.ILoginPresenter presenter;
    private LottieAnimationView lottieAnimationView_Loader;
    private TextInputLayout txt_username;
    private TextInputLayout txt_password;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_view);

        presenter = new LoginPresenter(this, new CustomerModel(), LoginService.getInstance());
        Toolbar toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        txt_username = findViewById(R.id.txt_username);

        txt_password = findViewById(R.id.txt_password);

        lottieAnimationView_Loader = findViewById(R.id.progressBar_loader);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                presenter.onLoginButtonClicked(
                        txt_username.getEditText().getText().toString(),
                        txt_password.getEditText().getText().toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onSuccess() {
        this.finish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivityView.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivityView.this, MainActivityView.class);
                startActivity(intent);
            }
        });
        UserCredentials.getInstance().setUsername(txt_username.getEditText().getText().toString());
        UserCredentials.isLoggedIn = true;
    }

    @Override
    public void onError(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snack = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                View view = snack.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(LoginActivityView.this, android.R.color.holo_orange_dark));
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error_24, 0, 0, 0);
                tv.setGravity(Gravity.CENTER);
                snack.show();
            }
        });

    }

    @Override
    public void displayProgressLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_Loader.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void hideProgressLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_Loader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setErrorUsername() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_username.setError("Empty Username");
            }
        });
    }

    @Override
    public void setErrorPassword() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_password.setError("Empty Password");
            }
        });
    }

    @Override
    public void removeErrorUsername() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_username.setError(null);
            }
        });
    }

    @Override
    public void removeErrorPassword() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_password.setError(null);
            }
        });

    }

}