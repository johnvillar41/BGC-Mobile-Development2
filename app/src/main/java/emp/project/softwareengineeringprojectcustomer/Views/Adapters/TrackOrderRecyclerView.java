package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerOrdersModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class TrackOrderRecyclerView extends RecyclerView.Adapter<TrackOrderRecyclerView.MyViewHolder> {

    Context context;
    List<CustomerOrdersModel> list;

    public TrackOrderRecyclerView(Context context, List<CustomerOrdersModel> list) {
        this.context = context;
        this.list = list;
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

        Blob b = (Blob) model.getSpecificOrdersModel().getProduct_image();
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
        holder.txt_product_name.setText(model.getSpecificOrdersModel().getProduct_name());
        holder.txt_date_ordered.setText(model.getOrder_date());
        holder.txt_total_number_of_orders.setText(model.getSpecificOrdersModel().getTotal_orders());
        holder.txt_price.setText(String.valueOf(Integer.parseInt(model.getSpecificOrdersModel().getTotal_orders()) * Integer.parseInt(model.getSpecificOrdersModel().getProduct_price())));
        holder.txt_total_order.setText(model.getTotal_number_of_orders());
        holder.txt_total_price.setText(model.getOrder_price());
    }

    private CustomerOrdersModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_order_id, txt_status, txt_product_name, txt_date_ordered, txt_total_number_of_orders, txt_price,txt_total_order,txt_total_price;
        ImageView imageView_product;

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
        }
    }
}
