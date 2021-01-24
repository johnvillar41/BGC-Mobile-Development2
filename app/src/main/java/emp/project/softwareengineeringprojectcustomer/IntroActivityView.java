package emp.project.softwareengineeringprojectcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.LoginActivityView;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.RegisterActivityView;

public class IntroActivityView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_view);

        ImageView circleImageView_logo = findViewById(R.id.image_logo);
        Glide.with(this)
                .load(R.drawable.logo)
                .centerInside()
                .fitCenter()
                .circleCrop()
                .into(circleImageView_logo);

        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
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