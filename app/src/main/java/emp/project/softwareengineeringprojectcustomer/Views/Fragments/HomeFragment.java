package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.HomeService;
import emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.HomeCategoryRecyclerView;

public class HomeFragment extends Fragment implements IHome.IHomeView {
    private RecyclerView recyclerView_Category, recyclerView_Home;
    private LottieAnimationView lottieAnimationView_loading;
    private IHome.IHomePresenter presenter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        presenter=new HomePresenter(this, HomeService.getINSTANCE());

        recyclerView_Category = rootView.findViewById(R.id.recyclerView_Category);
        recyclerView_Home = rootView.findViewById(R.id.recyclerView_Home);
        lottieAnimationView_loading = rootView.findViewById(R.id.progressBar);

        presenter.loadCategories();
        return rootView;
    }

    @Override
    public void displayProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_loading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView_loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void displayRecyclerViewCategory(List<String> categories) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(HomeFragment.this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                HomeCategoryRecyclerView adapter = new HomeCategoryRecyclerView(
                        categories,HomeFragment.this.getActivity());
                recyclerView_Category.setLayoutManager(layoutManager);
                recyclerView_Category.setAdapter(adapter);
                recyclerView_Category.scheduleLayoutAnimation();
            }
        });
    }

    @Override
    public void displayRecyclerViewHomeProducts(List<ProductModel> productModelLists) {


    }


}