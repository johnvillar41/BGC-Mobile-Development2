package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;
import emp.project.softwareengineeringprojectcustomer.R;

public class InformationRecyclerView extends RecyclerView.Adapter<InformationRecyclerView.MyViewHolder> {

    Context context;
    List<InformationModel> list;

    public InformationRecyclerView(Context context, List<InformationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_adapter_recycler_information, parent, false);
        return new InformationRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InformationModel informationModel = getItem(position);
        Blob b = (Blob) informationModel.getProductModel().getProduct_picture();
        int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(holder.imageView_Product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.textView_ProductName.setText(informationModel.getProductModel().getProduct_name());
        holder.cardView_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_information, null);
                dialogBuilder.setView(dialogView);

                ImageView imageView_product = dialogView.findViewById(R.id.image_product);
                TextView txt_product_name = dialogView.findViewById(R.id.txt_product_name);
                TextView txt_description = dialogView.findViewById(R.id.txt_description);
                TextView txt_information = dialogView.findViewById(R.id.txt_information);
                Blob b = (Blob) informationModel.getProductModel().getProduct_picture();
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
                txt_product_name.setText(informationModel.getProductModel().getProduct_name());
                txt_description.setText(informationModel.getProductModel().getProduct_description());
                txt_information.setText(informationModel.getProduct_information_tutorial());


                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private InformationModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_Product;
        TextView textView_ProductName;
        CardView cardView_Item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Product = itemView.findViewById(R.id.image_product);
            textView_ProductName = itemView.findViewById(R.id.txt_product_title);
            cardView_Item = itemView.findViewById(R.id.cardView_InformationItem);
        }
    }
}
