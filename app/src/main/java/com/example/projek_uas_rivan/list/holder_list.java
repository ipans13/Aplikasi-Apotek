package com.example.projek_uas_rivan.list;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_uas_rivan.R;

public class holder_list extends RecyclerView.ViewHolder {

    ImageView imageView;

    TextView namaView, jenisView, kadaluarsaView, deskripsiView, hargaView, jmlView;

    Button removebtn, updatebtn;
    ImageView gambarobt;
    LinearLayout cekdetail;
    public holder_list(View itemView, adapter_list.onClick listener){
        super(itemView);
        imageView = itemView.findViewById(R.id.list_image);
        namaView = itemView.findViewById(R.id.nama_obat);
        jenisView = itemView.findViewById(R.id.jenis_obat);
        kadaluarsaView = itemView.findViewById(R.id.Kadaluarsa_obat);
        deskripsiView = itemView.findViewById(R.id.deskripsi_obat);
        hargaView = itemView.findViewById(R.id.harga_obat);
        removebtn = itemView.findViewById(R.id.remove);
        updatebtn = itemView.findViewById(R.id.update);
        cekdetail = itemView.findViewById(R.id.cekobat);
        jmlView = itemView.findViewById(R.id.jml_obat);


    }

}
