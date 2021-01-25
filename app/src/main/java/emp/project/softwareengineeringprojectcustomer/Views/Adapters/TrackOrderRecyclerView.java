package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class TrackOrderRecyclerView extends RecyclerView.Adapter<TrackOrderRecyclerView.MyViewHolder> {

    Context context;
    List<CustomerOrdersModel> list;
    ITrackOrder.ITrackOrderService service;

    public TrackOrderRecyclerView(Context context, List<CustomerOrdersModel> list, ITrackOrder.ITrackOrderService service) {
        this.context = context;
        this.list = list;
        this.service = service;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_adapter_recycler_track_orders, parent, false);
        return new TrackOrderRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerOrdersModel model = getItem(position);

        Blob b = (Blob) model.getSpecificOrdersModelList().get(0).getProduct_image();
        int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(holder.imageView_product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        holder.txt_order_id.setText(model.getOrder_id());
        holder.txt_status.setText(model.getOrder_status());
        holder.txt_product_name.setText(model.getSpecificOrdersModelList().get(0).getProduct_name());
        holder.txt_date_ordered.setText(model.getOrder_date());
        holder.txt_total_number_of_orders.setText(model.getSpecificOrdersModelList().get(0).getTotal_orders());
        holder.txt_price.setText(String.valueOf(Integer.parseInt(model.getSpecificOrdersModelList().get(0).getTotal_orders()) * Integer.parseInt(model.getSpecificOrdersModelList().get(0).getProduct_price())));
        holder.txt_total_order.setText(model.getTotal_number_of_orders());
        holder.txt_total_price.setText(model.getOrder_price());

        holder.btn_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_specific_orders, null);

                List<SpecificOrdersModel> finalList = new ArrayList<>();

                for(SpecificOrdersModel specificOrdersModel:model.getSpecificOrdersModelList()){
                    if(specificOrdersModel.getOrder_id().equals(model.getOrder_id())){
                        finalList.add(specificOrdersModel);
                    }
                }

                RecyclerView recyclerView_Specific_Orders = dialogView.findViewById(R.id.recyclerView_Specific_Orders);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                SpecificOrdersRecyclerView adapter = new SpecificOrdersRecyclerView(
                        context, finalList);
                recyclerView_Specific_Orders.setLayoutManager(layoutManager);
                recyclerView_Specific_Orders.setAdapter(adapter);
                recyclerView_Specific_Orders.scheduleLayoutAnimation();

                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }


        });
    }

    private CustomerOrdersModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_order_id, txt_status, txt_product_name, txt_date_ordered, txt_total_number_of_orders, txt_price, txt_total_order, txt_total_price;
        ImageView imageView_product;
        Button btn_see_more;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_date_ordered = itemView.findViewById(R.id.txt_date_ordered);
            txt_total_number_of_orders = itemView.findViewById(R.id.txt_number_of_orders);
            txt_price = itemView.findViewById(R.id.txt_product_price);
            imageView_product = itemView.findViewById(R.id.image_product);
            txt_total_order = itemView.findViewById(R.id.txt_order_total);
            txt_total_price = itemView.findViewById(R.id.total_price);
            btn_see_more = itemView.findViewById(R.id.btn_see_more);
        }
    }
}
