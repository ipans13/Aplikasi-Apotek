package com.example.projek_uas_rivan.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projek_uas_rivan.Cookies;
import com.example.projek_uas_rivan.Database;
import com.example.projek_uas_rivan.LoginActivity;
import com.example.projek_uas_rivan.MainActivity;
import com.example.projek_uas_rivan.R;
import com.example.projek_uas_rivan.list.adapter_list;
import com.example.projek_uas_rivan.list.daftarObatFragment;
import com.example.projek_uas_rivan.list.list_item_obat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    Cookies cookies;
    Database database;
    final Calendar Kalender = Calendar.getInstance();
    List<list_pradakaluarsa_obat> items;
    String loclat;
    RecyclerView recylist;
    AlertDialog.Builder popup;
    EditText expobat;
    TextView alljmlobat, allempobat,location, user, userid, loctext;
    adapter_prakadaluarsa adapter;
    View view,dialogview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_home,container, false);
        dialogview = inflater.inflate(R.layout.popup_updobat, container, false);

        cookies = Cookies.getInstance();
        int id = cookies.getId();
        database = new Database(getActivity());
        user = view.findViewById(R.id.user_info);
        userid = view.findViewById(R.id.user_idinfo);
        loctext = view.findViewById(R.id.link_loc);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE id_user = '" + id + "'", null);
        String nama ="";
        String tempat = "";
        if(cursor.moveToFirst()) {

            nama = cursor.getString(1);
            tempat = cursor.getString(3);
            loclat = cursor.getString(4);
            cursor.close();
        }
        user.setText( nama);
        userid.setText(Integer.toString(id));
        loctext.setText(tempat);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        recylist = view.findViewById(R.id.prakadaluarsa_recy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recylist.setLayoutManager(layoutManager);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd-MM-yyyy " );
        LocalDateTime sekarang = LocalDateTime.now();
        String waktu = dtf.format(sekarang).toString();

        items = database.getItemshome(waktu,"tgl_exp","=");
        adapter = new adapter_prakadaluarsa(getActivity(), items);
        recylist.setAdapter(adapter);

        LinearLayout linearLayout = view.findViewById(R.id.banner_prakadaluarsa);
        int padding_height = screenheight * 2 /100;
        linearLayout.setPadding(0, padding_height,0,padding_height /2);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        linearLayout.setLayoutParams(layoutParams);
        String name_loc = String.valueOf(R.string.loc_name);


        location = view.findViewById(R.id.link_loc);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.co.id/maps/place/"+loclat);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        alljmlobat = view.findViewById(R.id.jumlah_Obat);
        int count = database.getjumlahobat("SELECT COUNT(*) FROM obat");
        String Stcount = Integer.toString(count);
        alljmlobat.setText(Stcount);


        allempobat = view.findViewById(R.id.Obat_Kosong);
        int countemp = database.getjumlahobat("SELECT COUNT(*) FROM obat where jml = 0");
        String Stcountemp = Integer.toString(countemp);
        allempobat.setText(Stcountemp);


        // Inflate the layout for this fragment

        adapter.setonClickUpdate(new adapter_prakadaluarsa.onClickUpdate() {
            @Override
            public void onItemclick(int position) {
                int id = items.get(position).getId();
                String kataid = Integer.toString(id);

                List<list_pradakaluarsa_obat> item = database.getItemshome(kataid, "id");
                EditText nmobat = dialogview.findViewById(R.id.Text_obat);
                EditText jmobat = dialogview.findViewById(R.id.Text_jumlah);
                EditText deskobat = dialogview.findViewById(R.id.text_desk);
                expobat = dialogview.findViewById(R.id.text_kadaluarsa);
                EditText hrgobat = dialogview.findViewById(R.id.text_harga);
                Spinner jenobat = dialogview.findViewById(R.id.spinner_Jenis);
                Button update = dialogview.findViewById(R.id.btnupd_obat);
                Button batal = dialogview.findViewById(R.id.btnbtl_obat);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Jenis, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                jenobat.setAdapter(adapter);

                expobat.setOnClickListener(v -> {
                    selectkalender();
                });

                int spinnerPosition = adapter.getPosition(item.get(0).getJenis().toString());
                nmobat.setText(item.get(0).getNama());
                jenobat.setSelection(spinnerPosition);
                jmobat.setText(item.get(0).getJmobat());
                deskobat.setText(item.get(0).getDeskripsi());
                expobat.setText(item.get(0).getKadaluarsa());
                hrgobat.setText(item.get(0).getHarga());
                popup = new AlertDialog.Builder(getActivity());
                popup.setView(dialogview);
                AlertDialog dialog = popup.create();
                dialog.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pesan  = database.updatedatabase(id,nmobat.getText().toString(), jenobat.getSelectedItem().toString(),expobat.getText().toString(),hrgobat.getText().toString(),deskobat.getText().toString(),jmobat.getText().toString());
                        showWarningDialog(pesan);
                        dialog.dismiss();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmenthome, fragment);
                        transaction.commit();
                        items.clear();
                        refreshlist();

                    }
                });
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmenthome, fragment);
                        transaction.commit();
                        items.clear();
                        refreshlist();

                    }
                });

            }
        });

        ImageView logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookies.setId(0);
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });
        return view;

    }
    public void onResume(){
        super.onResume();
        adapter.setonClickUpdate(new adapter_prakadaluarsa.onClickUpdate() {
            @Override
            public void onItemclick(int position) {
                int id = items.get(position).getId();
                String kataid = Integer.toString(id);

                List<list_pradakaluarsa_obat> item = database.getItemshome(kataid, "id");
                EditText nmobat = dialogview.findViewById(R.id.Text_obat);
                EditText jmobat = dialogview.findViewById(R.id.Text_jumlah);
                EditText deskobat = dialogview.findViewById(R.id.text_desk);
                expobat = dialogview.findViewById(R.id.text_kadaluarsa);
                EditText hrgobat = dialogview.findViewById(R.id.text_harga);
                Spinner jenobat = dialogview.findViewById(R.id.spinner_Jenis);
                Button update = dialogview.findViewById(R.id.btnupd_obat);
                Button batal = dialogview.findViewById(R.id.btnbtl_obat);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Jenis, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                jenobat.setAdapter(adapter);

                expobat.setOnClickListener(v -> {
                    selectkalender();
                });

                int spinnerPosition = adapter.getPosition(item.get(0).getJenis().toString());
                nmobat.setText(item.get(0).getNama());
                jenobat.setSelection(spinnerPosition);
                jmobat.setText(item.get(0).getJmobat());
                deskobat.setText(item.get(0).getDeskripsi());
                expobat.setText(item.get(0).getKadaluarsa());
                hrgobat.setText(item.get(0).getHarga());
                popup = new AlertDialog.Builder(getActivity());
                popup.setView(dialogview);
                AlertDialog dialog = popup.create();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pesan  = database.updatedatabase(id,nmobat.getText().toString(), jenobat.getSelectedItem().toString(),expobat.getText().toString(),hrgobat.getText().toString(),deskobat.getText().toString(),jmobat.getText().toString());
                        showWarningDialog(pesan);
                        dialog.dismiss();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmenthome, fragment);
                        transaction.commit();
                        items.clear();
                        refreshlist();

                    }
                });
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmenthome, fragment);
                        transaction.commit();
                        items.clear();
                        refreshlist();
                    }
                });
                dialog.show();
            }
        });
    }

    public void refreshlist(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        LocalDateTime sekarang = LocalDateTime.now();
        String waktu = dtf.format(sekarang).toString();
        items = database.getItemshome(waktu,"tgl_exp","=");
        adapter = new adapter_prakadaluarsa(getActivity(), items);
        recylist.setAdapter(adapter);
    }
    public void showWarningDialog( String isi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pesan");
        builder.setMessage(isi);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kosongkan dialog dan lanjutkan dengan tindakan yang sesuai
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void selectkalender(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Kalender.set(Calendar.DAY_OF_MONTH,day);
                Kalender.set(Calendar.MONTH, month);
                Kalender.set(Calendar.YEAR, year);
                expobat.setText(updateTanggal());

            }

        };
        expobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, Kalender.get(Calendar.DAY_OF_WEEK), Kalender.get(Calendar.MONTH), Kalender.get(Calendar.YEAR)).show();
            }
        });

    }
    private String updateTanggal(){
        String format = "dd-MM-YYYY";
        SimpleDateFormat formattanggal = new SimpleDateFormat(format, Locale.ENGLISH);
        return formattanggal.format(Kalender.getTime());
    }
}