package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.InformationService;
import emp.project.softwareengineeringprojectcustomer.Presenter.InformationPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.InformationRecyclerView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.SpecificOrdersRecyclerView;

public class InformationFragment extends Fragment implements IInformation.IInformationView {

    private RecyclerView recyclerView_Information;
    private LottieAnimationView lottieAnimationViewLoader;
    private IInformation.IInformationPresenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_information, container, false);
        recyclerView_Information = root.findViewById(R.id.recyclerView_Information);
        lottieAnimationViewLoader = root.findViewById(R.id.progressBar);
        presenter = new InformationPresenter(this, InformationService.getInstance());
        presenter.loadPageValues();
        return root;
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
    public void displayRecyclerViewValues(List<InformationModel> informationModelList) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    InformationRecyclerView adapter = new InformationRecyclerView(
                            InformationFragment.this.getActivity(), informationModelList);
                    recyclerView_Information.setLayoutManager(new GridLayoutManager(InformationFragment.this.getActivity(), 3));
                    recyclerView_Information.setAdapter(adapter);
                    recyclerView_Information.scheduleLayoutAnimation();
                }
            });
        }
    }
}