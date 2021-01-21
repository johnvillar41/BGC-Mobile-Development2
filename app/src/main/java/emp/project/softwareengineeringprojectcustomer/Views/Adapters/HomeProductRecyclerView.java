package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.ProductModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class HomeProductRecyclerView extends RecyclerView.Adapter<HomeProductRecyclerView.MyViewHolder> {

    Context context;
    IHome.IHomePresenter presenter;
    List<ProductModel> productList;

    public HomeProductRecyclerView(Context context, List<ProductModel> productList, IHome.IHomePresenter presenter) {
        this.context = context;
        this.productList = productList;
        this.presenter = presenter;
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
        holder.txt_product_stocks.setText(model.getProduct_stocks());
        holder.txt_product_description.setText(model.getProduct_description());
        holder.btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog(model);
            }
        });
    }

    private void displayAlertDialog(ProductModel model) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_order_number, null);

        ImageView imageView_product = dialogView.findViewById(R.id.image_product);
        Button btn_confirm = dialogView.findViewById(R.id.btn_confirm);
        CardView cardView_exit = dialogView.findViewById(R.id.exit);
        EditText editText_number_total = dialogView.findViewById(R.id.txt_total_orders);

        Blob b = (Blob) model.getProduct_picture();
        int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(imageView_product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_number_total.getText().toString().isEmpty() || Integer.parseInt(editText_number_total.getText().toString()) <= 0 ) {
                    Toast.makeText(context, "Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    model.setTotal_number_products_orders(editText_number_total.getText().toString());
                    presenter.onConfirmButtonClicked(editText_number_total.getText().toString(), model);
                    dialog.cancel();
                }
            }
        });

        cardView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
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
        TextView txt_product_name, txt_product_price, txt_product_stocks, txt_product_description;
        Button btn_buy_now;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            btn_buy_now = itemView.findViewById(R.id.btn_buy_now);
            txt_product_stocks = itemView.findViewById(R.id.txt_product_stocks);
            txt_product_description = itemView.findViewById(R.id.txt_product_description);
        }
    }
}
