package emp.project.softwareengineeringprojectcustomer.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Presenter.RegisterPresenter;
import emp.project.softwareengineeringprojectcustomer.R;

public class RegisterActivityView extends AppCompatActivity implements IRegister.IRegisterView {
    private IRegister.IRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_view);

        presenter = new RegisterPresenter(this, new CustomerModel(), this);

        TextInputLayout txt_username = findViewById(R.id.txt_username);
        TextInputLayout txt_password = findViewById(R.id.txt_password);
        TextInputLayout txt_password2 = findViewById(R.id.txt_password2);
        TextInputLayout txt_fullname = findViewById(R.id.txt_full_name);
        Button btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClicked(
                        txt_username.getEditText().getText().toString(),
                        txt_password.getEditText().getText().toString(),
                        txt_password2.getEditText().getText().toString(),
                        txt_fullname.getEditText().getText().toString());
            }
        });
    }
    @Override
    public void onSuccess() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_successfull_registration, null);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}