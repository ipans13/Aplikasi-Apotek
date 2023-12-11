package com.example.projek_uas_rivan.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_uas_rivan.R;

import java.util.List;

public class adapter_list extends RecyclerView.Adapter<holder_list> {

    Context context;

    List<list_item_obat> items;

    private onClick listener;
    public adapter_list(Context context, List<list_item_obat> items) {
        this.context = context;
        this.items = items;
    }

    public interface onClick{
        void onItemclick(int position, int btn);
    }

    public void setonClick(onClick click){
        listener = click;
    }


    public holder_list onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_obat_recy, parent, false);
        return new holder_list(view,listener);
    }

    ;

    public void onBindViewHolder(holder_list holder, int position) {
        int pst = position;
        String Deskcom = items.get(position).getDeskripsi();
        if (Deskcom.length() >= 60){
            Deskcom = items.get(position).getDeskripsi().substring(0,60);
        }
        holder.namaView.setText(items.get(position).getNama());
        holder.jenisView.setText(items.get(position).getJenis());
        holder.kadaluarsaView.setText(items.get(position).getKadaluarsa());
        holder.hargaView.setText(items.get(position).getHarga());
        holder.deskripsiView.setText(Deskcom);
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.jmlView.setText(items.get(position).getJmobat());
        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onItemclick(pst, 3);
                }
            }
        });
        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemclick(pst, 2);
                }
                }
        });
        holder.cekdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemclick(pst, 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
