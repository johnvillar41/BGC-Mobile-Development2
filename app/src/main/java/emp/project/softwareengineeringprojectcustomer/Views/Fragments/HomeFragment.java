package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ICart;
import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.HomeService;
import emp.project.softwareengineeringprojectcustomer.Presenter.HomePresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Activities.MainActivityView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.HomeCategoryRecyclerView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.HomeProductRecyclerView;

public class HomeFragment extends Fragment implements IHome.IHomeView {
    private RecyclerView recyclerView_Category, recyclerView_Home;
    private LottieAnimationView lottieAnimationView_loading_products, lottieAnimationView_loading_categories;
    private IHome.IHomePresenter presenter;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        view = rootView;
        presenter = new HomePresenter(this, HomeService.getINSTANCE());

        recyclerView_Category = rootView.findViewById(R.id.recyclerView_Category);
        recyclerView_Home = rootView.findViewById(R.id.recyclerView_Home);
        lottieAnimationView_loading_products = rootView.findViewById(R.id.progressBar);
        lottieAnimationView_loading_categories = rootView.findViewById(R.id.progressBarCategory);

        presenter.loadCategories();
        presenter.loadProducts();
        return rootView;
    }

    @Override
    public void displayProgressBarProducts() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loading_products.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgressBarProducts() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loading_products.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void displayProgressBarCategories() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loading_categories.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgressBarCategories() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loading_categories.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void displayRecyclerViewCategory(List<String> categories) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(HomeFragment.this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    HomeCategoryRecyclerView adapter = new HomeCategoryRecyclerView(
                            categories, HomeFragment.this.getActivity(), presenter);
                    recyclerView_Category.setLayoutManager(layoutManager);
                    recyclerView_Category.setAdapter(adapter);
                    recyclerView_Category.scheduleLayoutAnimation();
                }
            });
        }
    }

    @Override
    public void displayRecyclerViewHomeProducts(List<ProductModel> productModelLists) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(HomeFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
                    HomeProductRecyclerView adapter = new HomeProductRecyclerView(
                            HomeFragment.this.getActivity(), productModelLists, presenter);
                    recyclerView_Home.setLayoutManager(layoutManager);
                    recyclerView_Home.setAdapter(adapter);
                    recyclerView_Home.scheduleLayoutAnimation();
                }
            });
        }
    }

    @Override
    public void displayMessage(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                    View view = snack.getView();
                    TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                    tv.setTextColor(ContextCompat.getColor(HomeFragment.this.getActivity(), android.R.color.holo_orange_dark));
                    tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error_24, 0, 0, 0);
                    tv.setGravity(Gravity.CENTER);
                    snack.show();
                }
            });
        }
    }

    @Override
    public void displayTotalCartNumbers() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivityView.txt_total_cart.setVisibility(View.VISIBLE);
                    MainActivityView.txt_total_cart.setText(CartModel.getInstance().getTotalNumberOfOrders());
                }
            });
        }
    }

    @Override
    public void hideTotalCartNumbers() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivityView.txt_total_cart.setVisibility(View.INVISIBLE);
                }
            });
        }

    }
}