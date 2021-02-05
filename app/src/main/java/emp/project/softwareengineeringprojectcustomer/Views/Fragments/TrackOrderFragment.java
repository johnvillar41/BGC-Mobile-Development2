package emp.project.softwareengineeringprojectcustomer.Views.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.TrackOrderService;
import emp.project.softwareengineeringprojectcustomer.Presenter.TrackOrderPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.SortByRecyclerView;
import emp.project.softwareengineeringprojectcustomer.Views.Adapters.TrackOrderRecyclerView;

public class TrackOrderFragment extends Fragment implements ITrackOrder.ITrackOrderView {
    private ITrackOrder.ITrackOrderPresenter presenter;
    private RecyclerView recyclerView_orders;
    private LottieAnimationView lottieAnimationView_loader, lottieAnimationView_no_result;
    private TextView txtNoResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_track_order, container, false);
        recyclerView_orders = root.findViewById(R.id.recyclerView_orders);
        lottieAnimationView_loader = root.findViewById(R.id.loader_orders);
        lottieAnimationView_no_result = root.findViewById(R.id.no_result);
        txtNoResult = root.findViewById(R.id.txt_noREsult);
        presenter = new TrackOrderPresenter(this, TrackOrderService.getInstance());
        presenter.loadOrders();

        FloatingActionButton floatingActionButton = root.findViewById(R.id.fab_sort);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSortFloatinActionButtonClicked();
            }
        });

        return root;
    }

    @Override
    public void displayLoader() {
        if (getActivity() != null) {
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
        if (getActivity() != null) {
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
                            TrackOrderFragment.this.getActivity(), ordersList);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    recyclerView_orders.setLayoutManager(layoutManager);
                    recyclerView_orders.setAdapter(adapter);
                    recyclerView_orders.scheduleLayoutAnimation();
                }
            });
        }
    }

    private static final String SORT_BY_DATE = "Sort By Date";
    private static final String SORT_BY_FINISHED = "Show Finished Orders";
    private static final String SORT_BY_CANCELLED = "Show Cancelled Orders";
    private static final String SORT_BY_PROCESSING = "Sort By Processing";

    @Override
    public void displayPopupSortBy() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_sort, null);

        RecyclerView recyclerView_Sort = dialogView.findViewById(R.id.recyclerView_Sort);
        Button btn_confirm = dialogView.findViewById(R.id.btn_confirm);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        List<String> sortList = new ArrayList<>();
        sortList.add(SORT_BY_DATE);
        sortList.add(SORT_BY_FINISHED);
        sortList.add(SORT_BY_CANCELLED);
        sortList.add(SORT_BY_PROCESSING);
        SortByRecyclerView adapter = new SortByRecyclerView(this.getActivity(), sortList);
        recyclerView_Sort.setLayoutManager(layoutManager);
        recyclerView_Sort.setAdapter(adapter);
        recyclerView_Sort.scheduleLayoutAnimation();


        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    switch (SortByRecyclerView.getClickedString) {
                        case SORT_BY_DATE:
                            DatePickerDialog datePicker;
                            final Calendar calendar = Calendar.getInstance();
                            final int year = calendar.get(Calendar.YEAR);
                            final int month = calendar.get(Calendar.MONTH);
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                            datePicker = new DatePickerDialog(TrackOrderFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    calendar.set(year, month, dayOfMonth);
                                    String dateString = sdf.format(calendar.getTime());
                                    presenter.onDateSortConfirmClicked(dateString);
                                }
                            }, year, month, day);
                            datePicker.show();
                            break;
                        case SORT_BY_FINISHED:
                            presenter.onButtonConfirmSortClicked(SORT_TYPE.SORT_FINSIHED.getVal());
                            Toast.makeText(TrackOrderFragment.this.getActivity(), SORT_TYPE.SORT_FINSIHED.getVal(), Toast.LENGTH_SHORT).show();
                            break;
                        case SORT_BY_CANCELLED:
                            presenter.onButtonConfirmSortClicked(SORT_TYPE.SORT_CANCELLED.getVal());
                            Toast.makeText(TrackOrderFragment.this.getActivity(), SORT_TYPE.SORT_CANCELLED.getVal(), Toast.LENGTH_SHORT).show();
                            break;
                        case SORT_BY_PROCESSING:
                            presenter.onButtonConfirmSortClicked(SORT_TYPE.SORT_PROCESSING.getVal());
                            Toast.makeText(TrackOrderFragment.this.getActivity(), SORT_TYPE.SORT_PROCESSING.getVal(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(TrackOrderFragment.this.getActivity(), "Please put a check mark.", Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
                SortByRecyclerView.getClickedString = null;
            }
        });
    }

    @Override
    public void displayEmptyResult() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_no_result.setVisibility(View.VISIBLE);
                    txtNoResult.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideEmptyResult() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView_no_result.setVisibility(View.INVISIBLE);
                    txtNoResult.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public enum SORT_TYPE {
        SORT_FINSIHED("Finished"),
        SORT_CANCELLED("Cancelled"),
        SORT_PROCESSING("Processing");

        private String val;

        SORT_TYPE(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }
}