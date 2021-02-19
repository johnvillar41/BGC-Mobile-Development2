package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.RegisterService;
import emp.project.softwareengineeringprojectcustomer.Presenter.RegisterPresenter;
import emp.project.softwareengineeringprojectcustomer.R;

public class RegisterActivityView extends AppCompatActivity implements IRegister.IRegisterView {
    private IRegister.IRegisterPresenter presenter;
    private LottieAnimationView progressIndicator;
    private static ImageView PROFILE_PICTURE;
    private TextInputLayout txt_username;
    private TextInputLayout txt_password;
    private TextInputLayout txt_password2;
    private TextInputLayout txt_fullname;
    private TextInputLayout txt_email;
    private View view;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                view = v;
                List<String> textInputLayoutList;
                textInputLayoutList = new ArrayList<>();
                textInputLayoutList.add(txt_username.getEditText().getText().toString());
                textInputLayoutList.add(txt_password.getEditText().getText().toString());
                textInputLayoutList.add(txt_password2.getEditText().getText().toString());
                textInputLayoutList.add(txt_email.getEditText().getText().toString());
                textInputLayoutList.add(txt_fullname.getEditText().getText().toString());

                presenter.onRegisterButtonClicked(
                        textInputLayoutList,
                        FILE_INPUT_STREAM
                );
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
                Snackbar snack = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                View view = snack.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(RegisterActivityView.this, android.R.color.holo_orange_dark));
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error_24, 0, 0, 0);
                tv.setGravity(Gravity.CENTER);
                snack.show();
            }
        });
    }

    @Override
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }

    private static final int IMAGE_PICK_CODE = 123;
    private static InputStream FILE_INPUT_STREAM;

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
    public Boolean displayErrors() {
        final int[] errorCtr = {0};
        TextInputLayout[] textInputLayoutsArray = new TextInputLayout[5];
        textInputLayoutsArray[0] = txt_username;
        textInputLayoutsArray[1] = txt_password;
        textInputLayoutsArray[2] = txt_password2;
        textInputLayoutsArray[3] = txt_email;
        textInputLayoutsArray[4] = txt_fullname;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (TextInputLayout textInputLayout : textInputLayoutsArray) {
                    if (textInputLayout.getEditText().getText().toString().trim().isEmpty()) {
                        textInputLayout.setError("Error!");
                        errorCtr[0]++;
                    } else {
                        textInputLayout.setError(null);
                    }
                }
                if (textInputLayoutsArray[1].getEditText().getText().toString().equals(textInputLayoutsArray[2].getEditText().getText().toString()) &&
                        textInputLayoutsArray[1].getEditText().getText().toString().isEmpty() || textInputLayoutsArray[2].getEditText().getText().toString().isEmpty()) {
                    textInputLayoutsArray[1].setError("Error!");
                    textInputLayoutsArray[2].setError("Error!");
                    errorCtr[0]++;
                } else {
                    textInputLayoutsArray[1].setError(null);
                    textInputLayoutsArray[2].setError(null);
                }

                if (FILE_INPUT_STREAM == null) {
                    onError("Empty Image!");
                    errorCtr[0]++;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(textInputLayoutsArray[3].getEditText().getText().toString()).matches()) {
                    errorCtr[0]++;
                    textInputLayoutsArray[3].setError("Error Email!");
                } else {
                    textInputLayoutsArray[3].setError(null);
                }
            }
        });

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (errorCtr[0] == 0) {
            return true;
        } else {
            return false;
        }
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
    protected void onDestroy() {
        PROFILE_PICTURE = null;
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