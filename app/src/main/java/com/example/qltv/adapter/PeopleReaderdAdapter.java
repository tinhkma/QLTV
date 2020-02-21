package com.example.qltv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.R;
import com.example.qltv.model.PeopleLeaseModel;
import com.example.qltv.ui.ItemClick;

import java.util.List;

import butterknife.ButterKnife;

public class PeopleReaderdAdapter extends RecyclerView.Adapter<PeopleReaderdAdapter.ViewHoler> {

    private Context mContext;
    private List<PeopleLeaseModel> datas;

    public PeopleReaderdAdapter(Context mContext, List<PeopleLeaseModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_people_layout, parent, false);
        ButterKnife.bind(mContext, view);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.tvId.setText(datas.get(position).getId());
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPhone.setText(datas.get(position).getNumberPhone());
        holder.tvViolate.setText(String.valueOf(datas.get(position).getSumViolate()));
        if (datas.get(position).getSumViolate() > 1) {
            holder.tvViolate.setTextColor(Color.parseColor("#d74315"));
        }
        holder.lnItem.setOnClickListener(v-> {

        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        LinearLayout lnItem;
        TextView tvId;
        TextView tvName;
        TextView tvPhone;
        TextView tvViolate;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            tvViolate = itemView.findViewById(R.id.tv_violate);
            lnItem = itemView.findViewById(R.id.ln_item);
        }
    }
}
