package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Interface.ITrackOrder;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.SpecificOrdersModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class SpecificOrdersRecyclerView extends RecyclerView.Adapter<SpecificOrdersRecyclerView.MyViewHolder> {

    Context context;
    List<SpecificOrdersModel> list;

    public SpecificOrdersRecyclerView(Context context, List<SpecificOrdersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_recycler_specifc_orders, parent, false);
        return new SpecificOrdersRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SpecificOrdersModel model = getItem(position);
        Blob b = (Blob) model.getProduct_image();
        int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(holder.circleImageView_product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_number_of_orders.setText(model.getTotal_orders());
        holder.txt_product_price.setText(String.valueOf(Integer.parseInt(model.getProduct_price()) * Integer.parseInt(model.getTotal_orders())));
    }

    private SpecificOrdersModel getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView_product;
        TextView txt_product_name, txt_number_of_orders, txt_product_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView_product = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_number_of_orders = itemView.findViewById(R.id.txt_number_of_orders);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
        }
    }
}
