package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.RegisterService;
import emp.project.softwareengineeringprojectcustomer.Presenter.RegisterPresenter;
import emp.project.softwareengineeringprojectcustomer.R;

public class RegisterActivityView extends AppCompatActivity implements IRegister.IRegisterView {
    private IRegister.IRegisterPresenter presenter;
    private LottieAnimationView progressIndicator;
    private TextInputLayout txt_username;
    private TextInputLayout txt_password;
    private TextInputLayout txt_password2;
    private TextInputLayout txt_fullname;
    private TextInputLayout txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_view);

        presenter = new RegisterPresenter(this, new CustomerModel(), RegisterService.getInstance());

        Toolbar toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_password2 = findViewById(R.id.txt_password2);
        txt_fullname = findViewById(R.id.txt_full_name);
        txt_email = findViewById(R.id.txt_email);
        Button btn_register = findViewById(R.id.btn_register);
        PROFILE_PICTURE = findViewById(R.id.image_profile_picture);
        progressIndicator = findViewById(R.id.progressIndicator);

        PROFILE_PICTURE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onImageButtonClicked();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClicked(
                        txt_username.getEditText().getText().toString(),
                        txt_password.getEditText().getText().toString(),
                        txt_password2.getEditText().getText().toString(),
                        txt_fullname.getEditText().getText().toString(),
                        txt_email.getEditText().getText().toString(),
                        FILE_INPUT_STREAM);
            }
        });
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivityView.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_successfull_registration, null);
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @Override
    public void onError(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivityView.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void displayLoadingCircle() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoadingCircler() {
        progressIndicator.setVisibility(View.GONE);
    }

    private static final int IMAGE_PICK_CODE = 123;
    private static InputStream FILE_INPUT_STREAM;
    private static CircleImageView PROFILE_PICTURE;

    @Override
    public void loadImageFromGallery() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressIndicator.setVisibility(View.VISIBLE);
                    }
                });
                Bitmap originBitmap = null;
                Uri selectedImage;
                try {
                    selectedImage = data.getData();
                } catch (NullPointerException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressIndicator.setVisibility(View.GONE);
                        }
                    });
                    return;
                }

                InputStream imageStream;

                if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK
                        && null != data) {

                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        originBitmap = BitmapFactory.decodeStream(imageStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (originBitmap != null) {
                        final Bitmap finalOriginBitmap = originBitmap;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PROFILE_PICTURE.setImageBitmap(finalOriginBitmap);
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap image = ((BitmapDrawable) PROFILE_PICTURE.getDrawable()).getBitmap();
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                        FILE_INPUT_STREAM = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressIndicator.setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                });
                                thread1.start();
                            }
                        });
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}