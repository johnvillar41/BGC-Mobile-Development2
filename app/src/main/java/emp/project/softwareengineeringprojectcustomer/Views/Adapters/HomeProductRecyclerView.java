package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class HomeProductRecyclerView extends RecyclerView.Adapter<HomeProductRecyclerView.MyViewHolder> {

    Context context;
    List<ProductModel> productList;

    public HomeProductRecyclerView(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_recycler_product, parent, false);
        return new HomeProductRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //display picture using
        ProductModel model = getPosition(position);
        Blob b = (Blob) model.getProduct_picture();
        int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(holder.product_image);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_product_price.setText(model.getProduct_price());
        holder.txt_product_name.setText(model.getProduct_name());
        holder.btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private ProductModel getPosition(int position) {
        return productList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView txt_product_name, txt_product_price;
        Button btn_buy_now;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            btn_buy_now = itemView.findViewById(R.id.btn_buy_now);
        }
    }
}
