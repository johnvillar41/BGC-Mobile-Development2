package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class CheckoutRecyclerView extends RecyclerView.Adapter<CheckoutRecyclerView.MyViewHolder> {

    Context context;
    List<ProductModel> list;
    ICheckout.ICheckoutPresenter presenter;

    public CheckoutRecyclerView(Context context, List<ProductModel> list, ICheckout.ICheckoutPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_adapter_recycler_checkout, parent, false);
        return new CheckoutRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
                    .into(holder.imageView_product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_product_totalOrder.setText(model.getTotal_number_products_orders());
        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_productPrice.setText(String.valueOf(CartModel.getInstance().calculatePerProduct(position)));
        holder.cardView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                presenter.loadCartTotals();
                if (Integer.parseInt(CartModel.getInstance().getTotalNumberOfOrders()) <= 0) {
                    presenter.loadOrders();
                }
            }
        });
    }

    private ProductModel getPosition(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_product;
        TextView txt_product_name, txt_product_totalOrder;
        TextView txt_productPrice;
        CardView cardView_exit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_product = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_totalOrder = itemView.findViewById(R.id.txt_total_orders);
            cardView_exit = itemView.findViewById(R.id.remove);
            txt_productPrice = itemView.findViewById(R.id.txt_product_price);
        }
    }
}
