package com.roman.zadanie.ui.charges;

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
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.ui.incomes.IncomesAdapter;


public class ChargesAdapter extends ListAdapter<Charge,ChargesAdapter.ViewHolder> {


    private ChargeOnItemClickListener listener;

    public interface ChargeOnItemClickListener{
        void onItemClick(Charge charge);
    }


    public void setOnIncomeClickListener(ChargesAdapter.ChargeOnItemClickListener listener){
        this.listener = listener;
    }


    public ChargesAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Charge> DIFF_CALLBACK = new DiffUtil.ItemCallback<Charge>() {

        @Override
        public boolean areItemsTheSame(@NonNull Charge oldItem, @NonNull Charge newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Charge oldItem, @NonNull Charge newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ChargesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rate_item_layout,parent,false);
        return new ChargesAdapter.ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChargesAdapter.ViewHolder holder, int position) {
        Charge currentItem = getItem(position);
        holder.nameField.setText(currentItem.getName());
        holder.priceField.setText(String.valueOf(currentItem.getPrice()));
        holder.categoryField.setText(currentItem.getCategory());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameField;
        private TextView priceField;
        private TextView categoryField;

        public ViewHolder(@NonNull View itemView, final ChargeOnItemClickListener listener) {
            super(itemView);
            nameField = itemView.findViewById(R.id.nameField);
            priceField = itemView.findViewById(R.id.priceField);
            categoryField = itemView.findViewById(R.id.categoryField);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            listener.onItemClick(
                                    getCurrentList().get(position)
                            );
                        }
                    }
                }
            });
        }
    }
}
