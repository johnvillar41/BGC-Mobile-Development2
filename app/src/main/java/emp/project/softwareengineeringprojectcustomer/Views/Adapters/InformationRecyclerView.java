package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        /*Blob b = (Blob) informationModel.getProduct_image();
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
        }*/
        holder.textView_ProductDescription.setText(informationModel.getProduct_name());
        holder.cardView_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:IMPLEMENT ACTIVITY TRANSITION
                Toast.makeText(context, "Go to new page huehue", Toast.LENGTH_SHORT).show();
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
        TextView textView_ProductDescription;
        CardView cardView_Item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Product = itemView.findViewById(R.id.image_product);
            textView_ProductDescription = itemView.findViewById(R.id.txt_product_title);
            cardView_Item = itemView.findViewById(R.id.cardView_InformationItem);
        }
    }
}
