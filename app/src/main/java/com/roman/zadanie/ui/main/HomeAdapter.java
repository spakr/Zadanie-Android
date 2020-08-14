package com.roman.zadanie.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.zadanie.R;
import com.roman.zadanie.db.entity.CategoryPrice;
import com.roman.zadanie.ui.charges.ChargesAdapter;

import org.w3c.dom.Text;

public class HomeAdapter extends ListAdapter<CategoryPrice, HomeAdapter.ViewHolder> {

    private CategoryOnClickListener listener;

    public interface CategoryOnClickListener{
        void onItemClick(int position);
    }

    public HomeAdapter() {
        super(DIFF_CALLBACK);

    }

    public void setOnCategoryClickListener(CategoryOnClickListener listener){
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<CategoryPrice> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategoryPrice>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoryPrice oldItem, @NonNull CategoryPrice newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryPrice oldItem, @NonNull CategoryPrice newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoryprice_item_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        CategoryPrice currentItem = getItem(position);
        holder.priceField.setText(String.valueOf(currentItem.getPrice())+ " €");
        holder.categoryField.setText(currentItem.getCategory());
        holder.typeField.setText(String.valueOf(currentItem.type));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView priceField;
        private TextView categoryField;
        private TextView typeField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceField = itemView.findViewById(R.id.priceField);
            categoryField = itemView.findViewById(R.id.categoryField);
            typeField = itemView.findViewById(R.id.typeField);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(
                                    position
                            );
                        }
                    }
                }
            });
        }
    }

}