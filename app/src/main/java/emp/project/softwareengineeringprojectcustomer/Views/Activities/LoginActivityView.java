package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

import emp.project.softwareengineeringprojectcustomer.Interface.ILogin;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.LoginService;
import emp.project.softwareengineeringprojectcustomer.Presenter.LoginPresenter;
import emp.project.softwareengineeringprojectcustomer.R;

public class LoginActivityView extends AppCompatActivity implements ILogin.ILoginView {
    private ILogin.ILoginPresenter presenter;
    private LottieAnimationView lottieAnimationView_Loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_view);

        presenter = new LoginPresenter(this, new CustomerModel(),LoginService.getInstance());
        Toolbar toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        TextInputLayout txt_username = findViewById(R.id.txt_username);
        TextInputLayout txt_password = findViewById(R.id.txt_password);

        lottieAnimationView_Loader = findViewById(R.id.progressBar_loader);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }

    @Override
    public void onError(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivityView.this, errorMessage, Toast.LENGTH_SHORT).show();
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

}