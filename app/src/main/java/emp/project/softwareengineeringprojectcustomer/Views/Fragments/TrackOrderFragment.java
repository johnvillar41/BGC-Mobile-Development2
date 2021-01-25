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

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.TrackOrderService;
import emp.project.softwareengineeringprojectcustomer.Presenter.TrackOrderPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.HomeCategoryRecyclerView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.TrackOrderRecyclerView;

public class TrackOrderFragment extends Fragment implements ITrackOrder.ITrackOrderView {
    private ITrackOrder.ITrackOrderPresenter presenter;
    private RecyclerView recyclerView_orders;
    private LottieAnimationView lottieAnimationView_loader;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_track_order, container, false);
        recyclerView_orders = root.findViewById(R.id.recyclerView_orders);
        lottieAnimationView_loader = root.findViewById(R.id.loader_orders);
        presenter = new TrackOrderPresenter(this, TrackOrderService.getInstance());
        presenter.loadOrders();
        return root;
    }

    @Override
    public void displayLoader() {
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loader.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideLoader() {
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_loader.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void displayOrders(List<CustomerOrdersModel> ordersList) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(TrackOrderFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false);
                    TrackOrderRecyclerView adapter = new TrackOrderRecyclerView(
                            TrackOrderFragment.this.getActivity(), ordersList,TrackOrderService.getInstance());
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    recyclerView_orders.setLayoutManager(layoutManager);
                    recyclerView_orders.setAdapter(adapter);
                    recyclerView_orders.scheduleLayoutAnimation();
                }
            });
        }
    }
}