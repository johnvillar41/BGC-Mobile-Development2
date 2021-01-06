package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.R;

public class HomeFragment extends Fragment implements IHome.IHomeView {
    private RecyclerView recyclerView_Category, recyclerView_Home;
    private LottieAnimationView lottieAnimationView_loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView_Category = rootView.findViewById(R.id.recyclerView_Category);
        recyclerView_Home = rootView.findViewById(R.id.recyclerView_Home);
        lottieAnimationView_loading = rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void displayProgressBar() {
        lottieAnimationView_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        lottieAnimationView_loading.setVisibility(View.GONE);
    }
}