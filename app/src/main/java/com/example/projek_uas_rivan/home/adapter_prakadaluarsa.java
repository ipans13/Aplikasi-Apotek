package com.example.projek_uas_rivan.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_uas_rivan.R;
import com.example.projek_uas_rivan.list.adapter_list;
import com.example.projek_uas_rivan.list.holder_list;


import java.util.List;

public class adapter_prakadaluarsa extends RecyclerView.Adapter<holder_prakadaluarsa> {
    Context context;

    List<list_pradakaluarsa_obat> items;
    private onClickUpdate listener;
    public interface onClickUpdate{
        void onItemclick(int position);
    }
    public void setonClickUpdate(adapter_prakadaluarsa.onClickUpdate clickUpdate){
        listener = clickUpdate;
    }
    public adapter_prakadaluarsa(Context context, List<list_pradakaluarsa_obat> items){
        this.context = context;
        this.items = items;
    }

    public holder_prakadaluarsa onCreateViewHolder(ViewGroup parent, int viewType){
        View view =  LayoutInflater.from(context).inflate(R.layout.prakadaluarsa_recy,parent,false);
        return new holder_prakadaluarsa(view,listener);
    }
    public void onBindViewHolder(holder_prakadaluarsa holder, int position){
        int pst = position;
        holder.namaView.setText(items.get(position).getNama());
        holder.jenisView.setText(items.get(position).getJenis());
        holder.kadaluarsaView.setText(items.get(position).getKadaluarsa());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.jmlView.setText(items.get(position).getJmobat());
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemclick(pst);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
