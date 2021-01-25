package emp.project.softwareengineeringprojectcustomer.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import emp.project.softwareengineeringprojectcustomer.R;

public class SortByRecyclerView extends RecyclerView.Adapter<SortByRecyclerView.MyViewHolder> {

    Context context;
    List<String> sortList;
    int checkbox_position = -1;
    public static String getClickedString;

    public SortByRecyclerView(Context context, List<String> sortList) {
        this.context = context;
        this.sortList = sortList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_recycler_sort, parent, false);
        return new SortByRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = getItem(position);
        holder.txt_sort_title.setText(item);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox_position = position;
                getClickedString = item;
                notifyDataSetChanged();
            }
        });
        if (checkbox_position == position) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return sortList.size();
    }

    public String getItem(int position) {
        return sortList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_sort_title;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sort_title = itemView.findViewById(R.id.txt_sort_title);
            checkBox = itemView.findViewById(R.id.checkbox_sort);
        }
    }
}
