package com.footinit.motionlayoutplayground.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.footinit.motionlayoutplayground.R;
import com.footinit.motionlayoutplayground.model.CustomModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.MainViewHolder> {

    private List<CustomModel> list;
    private Callback callback;


    public MainGridAdapter(Callback callback, List<CustomModel> list) {
        this.callback = callback;
        this.list = list;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void removeCallback() {
        this.callback = null;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<CustomModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onItemSelected(int id, String message);
    }


    class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDescription)
        TextView tvItemName;

        @BindView(R.id.tvBrief)
        TextView tvBrief;

        @BindView(R.id.cvItem)
        CardView cvItem;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void onBind(int position) {
            CustomModel customModel = list.get(position);

            tvBrief.setText(customModel.getBrief());
            tvItemName.setText(customModel.getDescription());
            cvItem.setCardBackgroundColor(customModel.getColorCode());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onItemSelected(
                                position + 1,
                                customModel.getBrief() + " - " + customModel.getDescription());
                }
            });
        }
    }
}
