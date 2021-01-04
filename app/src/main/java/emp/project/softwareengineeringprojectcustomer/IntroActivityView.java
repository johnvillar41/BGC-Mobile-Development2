package emp.project.softwareengineeringprojectcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import emp.project.softwareengineeringprojectcustomer.Views.LoginActivityView;
import emp.project.softwareengineeringprojectcustomer.Views.RegisterActivityView;

public class IntroActivityView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_view);

        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            Intent intent = new Intent(this, LoginActivityView.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(this, RegisterActivityView.class);
            startActivity(intent);
        }
    }
}