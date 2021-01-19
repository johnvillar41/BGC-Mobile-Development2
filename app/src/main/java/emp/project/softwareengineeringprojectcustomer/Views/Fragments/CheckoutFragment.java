package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.CheckoutService;
import emp.project.softwareengineeringprojectcustomer.Presenter.CheckoutPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.CheckoutRecyclerView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.HomeCategoryRecyclerView;

public class CheckoutFragment extends Fragment implements ICheckout.ICheckoutView {
    private RecyclerView recyclerViewOrders;
    private TextView txtCartTotal;
    private Button btnCheckout;
    private LottieAnimationView lottieAnimationViewLoader;
    private ICheckout.ICheckoutPresenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);
        recyclerViewOrders = root.findViewById(R.id.recyclerView_Cart);
        txtCartTotal = root.findViewById(R.id.txt_total_value);
        btnCheckout = root.findViewById(R.id.btn_checkout);
        lottieAnimationViewLoader = root.findViewById(R.id.progressBar);

        presenter = new CheckoutPresenter(this, CheckoutService.getInstance());
        presenter.loadOrders();
        return root;
    }

    @Override
    public void displayCartOrders() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(CheckoutFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
                CheckoutRecyclerView adapter = new CheckoutRecyclerView(CheckoutFragment.this.getActivity(), CartModel.getInstance().getCartValues());
                recyclerViewOrders.setLayoutManager(layoutManager);
                recyclerViewOrders.setAdapter(adapter);
                recyclerViewOrders.scheduleLayoutAnimation();
            }
        });
    }

    @Override
    public void displayProgressLoader() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationViewLoader.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressLoader() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lottieAnimationViewLoader.setVisibility(View.INVISIBLE);
            }
        });
    }
}
