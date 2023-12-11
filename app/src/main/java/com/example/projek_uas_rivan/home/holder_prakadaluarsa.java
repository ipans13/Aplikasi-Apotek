package com.example.projek_uas_rivan.home;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_uas_rivan.R;

public class holder_prakadaluarsa extends RecyclerView.ViewHolder {

    ImageView imageView;

    TextView namaView, jenisView, kadaluarsaView, jmlView;

    Button btnupdate;
    public holder_prakadaluarsa(View itemView, adapter_prakadaluarsa.onClickUpdate listener){
        super(itemView);
        imageView = itemView.findViewById(R.id.prakadaluarsa_image);
        namaView = itemView.findViewById(R.id.prakadaluarsa_nama);
        jenisView = itemView.findViewById(R.id.prakadaluarsa_jenis);
        kadaluarsaView = itemView.findViewById(R.id.prakadaluarsa_date);
        btnupdate = itemView.findViewById(R.id.update_home);
        jmlView = itemView.findViewById(R.id.jml_obat);

    }
}
