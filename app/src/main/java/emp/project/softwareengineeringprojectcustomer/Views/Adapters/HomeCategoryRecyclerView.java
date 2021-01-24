 package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IHome;
import emp.project.softwareengineeringprojectcustomer.R;

public class HomeCategoryRecyclerView extends RecyclerView.Adapter<HomeCategoryRecyclerView.MyViewHolder> {

    List<String> categoryList;
    Context context;
    IHome.IHomePresenter presenter;
    int row_index = 0;

    public HomeCategoryRecyclerView(List<String> categoryList, Context context, IHome.IHomePresenter presenter) {
        this.categoryList = categoryList;
        this.context = context;
        this.presenter = presenter;
    }


    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_adapter_recycler_category, parent, false);
        return new HomeCategoryRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String category = getCategory(position);
        holder.txt_category.setText(category);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                presenter.onCategoryButtonClicked(category);
                notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            holder.cardView.setBackgroundResource(R.drawable.card_view_border);
        } else {
            holder.cardView.setBackgroundResource(R.drawable.card_view_border_unclicked);
        }
    }

    private String getCategory(int position) {
        return categoryList.get(position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_category;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            cardView = itemView.findViewById(R.id.cardView_category);
        }
    }
}
