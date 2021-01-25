package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
        View view = inflater.inflate(R.layout.custom_popup_specific_orders, parent, false);
        return new SpecificOrdersRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Toast.makeText(context, , Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView_product;
        TextView txt_product_name, txt_date, txt_number_of_orders, txt_product_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView_product = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_date = itemView.findViewById(R.id.txt_date_ordered);
            txt_number_of_orders = itemView.findViewById(R.id.txt_number_of_orders);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
        }
    }
}
