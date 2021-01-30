package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Interface.IUser;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.UserProfileService;
import emp.project.softwareengineeringprojectcustomer.Presenter.UserProfilePresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.MainActivityView;

public class UserProfileFragment extends Fragment implements IUser.IUserView {

    private TextView txt_username, txt_password, txt_email;
    private CircleImageView circleImageView_user;
    private LottieAnimationView lottieAnimationViewLoader;
    private IUser.IUserPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        txt_username = rootView.findViewById(R.id.txt_user_username);
        txt_email = rootView.findViewById(R.id.txt_user_email);
        txt_password = rootView.findViewById(R.id.txt_user_password);
        circleImageView_user = rootView.findViewById(R.id.image_profile_picture);
        lottieAnimationViewLoader = rootView.findViewById(R.id.progressBar);

        presenter = new UserProfilePresenter(this, UserProfileService.getInstance());
        presenter.loadCredentials();

        return rootView;
    }

    @Override
    public void displayLoader() {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationViewLoader.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideLoader() {
        if(getActivity()!=null){
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
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_email.setText(userModel.getUser_email());
                    txt_password.setText(userModel.getUser_password());
                    txt_username.setText(userModel.getUser_username());
                    Blob b = (Blob)userModel.getPicture();
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

    @Override
    public void displayUpdatePopup() {

    }
}
