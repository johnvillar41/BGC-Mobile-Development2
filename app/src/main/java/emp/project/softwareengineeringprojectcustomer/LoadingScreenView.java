package emp.project.softwareengineeringprojectcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadingScreenView extends AppCompatActivity {
    CircleImageView circleImageView;
    Animation animationUtils1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);

        circleImageView = findViewById(R.id.image_logo);
        animationUtils1 = AnimationUtils.loadAnimation(this, R.anim.loading1);

        circleImageView.setAnimation(animationUtils1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingScreenView.this, IntroActivityView.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}