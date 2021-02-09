package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.UserProfileService;
import emp.project.softwareengineeringprojectcustomer.Presenter.UserProfilePresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements IUser.IUserView, View.OnClickListener {

    private TextView txt_username, txt_password, txt_email, txt_user_id, txt_user_status;
    private CircleImageView circleImageView_user;
    private LottieAnimationView lottieAnimationViewLoader;
    private IUser.IUserPresenter presenter;
    private FloatingActionButton floatingActionButton_update;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        txt_username = rootView.findViewById(R.id.txt_user_username);
        txt_email = rootView.findViewById(R.id.txt_user_email);
        txt_password = rootView.findViewById(R.id.txt_user_password);
        txt_user_id = rootView.findViewById(R.id.txt_user_id);
        txt_user_status = rootView.findViewById(R.id.txt_user_status);
        circleImageView_user = rootView.findViewById(R.id.image_profile_picture);
        lottieAnimationViewLoader = rootView.findViewById(R.id.progressBar);
        floatingActionButton_update = rootView.findViewById(R.id.fab_update);

        presenter = new UserProfilePresenter(this, UserProfileService.getInstance());
        presenter.loadCredentials();
        floatingActionButton_update.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void displayProgressBar() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationViewLoader.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgressBar() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationViewLoader.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void displayUserCredentials(CustomerModel userModel) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_email.setText(userModel.getUser_email());
                    txt_password.setText(userModel.getUser_password());
                    txt_username.setText(userModel.getUser_username());
                    txt_user_id.setText(userModel.getUser_id());
                    txt_user_status.setText(userModel.getUser_status());
                    Blob b = (Blob) userModel.getPicture();
                    int[] blobLength = new int[1];
                    try {
                        blobLength[0] = (int) b.length();
                        byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                        Glide.with(UserProfileFragment.this.getActivity())
                                .load(blobAsBytes)
                                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                .skipMemoryCache(true)
                                .into(circleImageView_user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    //This is for the popup
    @SuppressLint("StaticFieldLeak")
    private static CircleImageView circleImageView_image_popup;
    private static final int IMAGE_PICK_CODE = 2;
    private static InputStream FILE_INPUT_STREAM;

    TextInputLayout txt_username_popup;
    TextInputLayout txt_password_popup;
    TextInputLayout txt_full_name_popup;
    TextInputLayout txt_email_popup;
    LottieAnimationView lottieAnimationView_loader_popup;
    AlertDialog dialog;
    public static boolean isUpdateButtonClicked = false;

    @Override
    public void displayUpdatePopup() {
        if (getActivity() != null) {
            try {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserProfileFragment.this.getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_update_userprofile, null);

                circleImageView_image_popup = dialogView.findViewById(R.id.profile_picture);
                Button btn_select_image = dialogView.findViewById(R.id.btn_select_picture);
                txt_username_popup = dialogView.findViewById(R.id.txt_username);
                txt_password_popup = dialogView.findViewById(R.id.txt_password);
                txt_full_name_popup = dialogView.findViewById(R.id.txt_full_name);
                txt_email_popup = dialogView.findViewById(R.id.txt_email);
                Button btn_update = dialogView.findViewById(R.id.btn_update);
                lottieAnimationView_loader_popup = dialogView.findViewById(R.id.progressBar);

                dialogBuilder.setView(dialogView);

                dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                btn_select_image.setOnClickListener(this);
                btn_update.setOnClickListener(this);

                presenter.loadPopupValues();

            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public void loadImageFromGallery() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, IMAGE_PICK_CODE);
                }
            });
        }
    }

    @Override
    public void displayUserCredentialsPopup(CustomerModel userModel) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (txt_username_popup.getEditText() != null &&
                            txt_password_popup.getEditText() != null &&
                            txt_full_name_popup.getEditText() != null &&
                            txt_email_popup.getEditText() != null) {
                        Blob b = (Blob) userModel.getPicture();
                        int[] blobLength = new int[1];
                        try {
                            blobLength[0] = (int) b.length();
                            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                            Glide.with(UserProfileFragment.this.getActivity())
                                    .load(blobAsBytes)
                                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                    .skipMemoryCache(true)
                                    .into(circleImageView_image_popup);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        txt_username_popup.getEditText().setText(userModel.getUser_username());
                        txt_password_popup.getEditText().setText(userModel.getUser_password());
                        txt_full_name_popup.getEditText().setText(userModel.getUser_fullname());
                        txt_email_popup.getEditText().setText(userModel.getUser_email());
                    }
                }
            });
        }
    }

    @Override
    public void displayProgressBarPopup() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loader_popup.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgressBarPopup() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loader_popup.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void logout() {
        if (getActivity() != null) {
            dialog.cancel();
            getActivity().finish();
            UserCredentials.getInstance().setUsername(null);
            UserCredentials.getInstance().logoutAccount();
        }
    }

    TextInputLayout[] arrTexts = new TextInputLayout[4];
    String[] textFieldData = new String[4];

    @Override
    public Boolean displayErrors() {
        int[] errorCtr = new int[1];
        if (txt_username_popup.getEditText() != null &&
                txt_password_popup.getEditText() != null &&
                txt_full_name_popup.getEditText() != null &&
                txt_email_popup.getEditText() != null) {
            arrTexts[0] = txt_username_popup;
            arrTexts[1] = txt_password_popup;
            arrTexts[2] = txt_full_name_popup;
            arrTexts[3] = txt_email_popup;

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (TextInputLayout textInputLayout : arrTexts) {
                            if (textInputLayout.getEditText().getText().toString().trim().isEmpty()) {
                                textInputLayout.setError("Empty Field!");
                                errorCtr[0]++;
                            } else {
                                textInputLayout.setError(null);
                            }
                        }
                    }
                });
            }
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (errorCtr[0] > 0) {
            return false;
        } else {
            for (int i = 0; i < textFieldData.length; i++) {
                textFieldData[i] = arrTexts[i].getEditText().getText().toString();
            }
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_select_picture) {
            presenter.onSelectImageButtonClicked();
        } else if (v.getId() == R.id.btn_update) {
            isUpdateButtonClicked = true;
            presenter.onUpdateProfileButtonClicked(FILE_INPUT_STREAM, textFieldData);
        } else if (v.getId() == R.id.fab_update) {
            presenter.onFloatingUpdateButtonClicked();
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //progressIndicator.setVisibility(View.VISIBLE);
                        }
                    });
                }
                Bitmap originBitmap = null;
                Uri selectedImage;
                try {
                    selectedImage = data.getData();
                } catch (NullPointerException e) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //progressIndicator.setVisibility(View.GONE);
                            }
                        });
                    }
                    return;
                }

                InputStream imageStream;

                if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {

                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        originBitmap = BitmapFactory.decodeStream(imageStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (originBitmap != null) {
                        final Bitmap finalOriginBitmap = originBitmap;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleImageView_image_popup.setImageBitmap(finalOriginBitmap);
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap image = ((BitmapDrawable) circleImageView_image_popup.getDrawable()).getBitmap();
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                        FILE_INPUT_STREAM = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //progressIndicator.setVisibility(View.GONE);
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
    public void onDestroy() {
        circleImageView_image_popup = null;
        FILE_INPUT_STREAM = null;
        super.onDestroy();
    }
}
