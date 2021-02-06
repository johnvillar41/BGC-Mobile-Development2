package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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

        presenter = new LoginPresenter(this, LoginService.getInstance());
        Toolbar toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);

        lottieAnimationView_Loader = findViewById(R.id.progressBar_loader);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (txt_username.getEditText() != null && txt_password.getEditText() != null) {
                    presenter.onLoginButtonClicked(
                            txt_username.getEditText().getText().toString(),
                            txt_password.getEditText().getText().toString());
                }
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
        UserCredentials.getInstance().loginAccount();
    }

    @Override
    public void displaySnackBarMessage(String errorMessage) {
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
    public Boolean displayErrors() {
        int[] errorCtr = new int[1];
        TextInputLayout[] textInputLayouts = new TextInputLayout[2];
        textInputLayouts[0] = txt_username;
        textInputLayouts[1] = txt_password;

        for (TextInputLayout txt : textInputLayouts) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (txt.getEditText() != null) {
                        if (txt.getEditText().getText().toString().trim().isEmpty()) {
                            txt.setError("Empty field!");
                            errorCtr[0]++;
                        } else {
                            txt.setError(null);
                        }
                    }
                }
            });
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (errorCtr[0] > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void displayPopupConfirmation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivityView.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_confirmation, null);

                TextInputLayout textInputLayout = dialogView.findViewById(R.id.txt_code);
                Button btn_submit = dialogView.findViewById(R.id.btn_submit);


                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        presenter.onSubmitCodeButtonClicked(textInputLayout.getEditText().getText().toString().trim(), txt_username.getEditText().getText().toString());
                    }
                });
            }
        });

    }

    @Override
    public void displayStatusMessage(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivityView.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}