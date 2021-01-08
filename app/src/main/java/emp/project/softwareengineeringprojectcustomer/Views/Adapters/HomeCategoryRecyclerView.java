package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCategoryRecyclerView extends RecyclerView.Adapter<HomeCategoryRecyclerView.MyViewHolder> {

    List<String> categoryList;
    Context context;

    public HomeCategoryRecyclerView(List<String> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
