package com.example.projek_uas_rivan.list;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.projek_uas_rivan.Cookies;
import com.example.projek_uas_rivan.Database;
import com.example.projek_uas_rivan.MainActivity;
import com.example.projek_uas_rivan.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class daftarObatFragment extends Fragment {

    private Button qrcode,sort;
    Database database;

    final Calendar Kalender = Calendar.getInstance();
    AlertDialog.Builder builder, popup;
    adapter_list adapter;
    EditText expobat;
    List<list_item_obat> items;
    RecyclerView recylist;
    View view,dialogview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_daftar_obat, container, false);
        dialogview = inflater.inflate(R.layout.popup_updobat, container, false);
        database = new Database(getActivity().getApplicationContext());
        items = database.getAllItems();
        adapter = new adapter_list(getActivity(), items);
        recylist = view.findViewById(R.id.list_obat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recylist.setLayoutManager(layoutManager);
        recylist.setAdapter(adapter);
        SearchView search = view.findViewById(R.id.src_item);
        search.setQueryHint("Cari");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                items = database.searchItems(s);
                adapter = new adapter_list(getContext(), items);
                recylist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        // qrcode
        qrcode = view.findViewById(R.id.qrcode);
        qrcode.setOnClickListener(v -> {
            scanCode();
        });
        adapter.setonClick(new adapter_list.onClick(){

            public void onItemclick(int position, int btn) {
                int id = items.get(position).getId();
                if (btn == 3) {
                    builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Yakin ingin menghapus obat ini?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            items.remove(position);
                            adapter.notifyItemRemoved(position);

                            String pesan = database.Delobat(id);
                            showWarningDialog(pesan);
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (btn == 2) {
                    String kataid = Integer.toString(id);
                    View dialogview = inflater.inflate(R.layout.popup_updobat, container, false);

                    List<list_item_obat> item = database.getItems(kataid, "id");
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
                            daftarObatFragment fragment = new daftarObatFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentlist, fragment); // Menggantikan Fragment saat ini dengan Fragment baru
                            transaction.commit();
                            refreshlist();



                        }
                    });
                    batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            FragmentManager fragmentManager = getChildFragmentManager();
                            daftarObatFragment fragment = new daftarObatFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentlist, fragment); // Menggantikan Fragment saat ini dengan Fragment baru
                            transaction.commit();
                            refreshlist();
                        }
                    });



                }
            }
        });

        return view;
    }

    private void refreshlist() {
        items = database.getAllItems();
        adapter = new adapter_list(getActivity(), items);
        recylist.setAdapter(adapter);
    }


    public void onResume(){
        super.onResume();
        adapter.setonClick(new adapter_list.onClick(){

            public void onItemclick(int position, int btn) {
                int id = items.get(position).getId();
                if (btn == 3) {
                    builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Yakin ingin menghapus obat ini?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            items.remove(position);
                            adapter.notifyItemRemoved(position);

                            String pesan = database.Delobat(id);
                            showWarningDialog(pesan);
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (btn == 2) {
                    String kataid = Integer.toString(id);

                    List<list_item_obat> item = database.getItems(kataid, "id");
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
                            daftarObatFragment fragment = new daftarObatFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentlist, fragment); // Menggantikan Fragment saat ini dengan Fragment baru
                            transaction.commit();
                            refreshlist();



                        }
                    });
                    batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            FragmentManager fragmentManager = getChildFragmentManager();
                            daftarObatFragment fragment = new daftarObatFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentlist, fragment); // Menggantikan Fragment saat ini dengan Fragment baru
                            transaction.commit();
                            refreshlist();
                        }
                    });
                    dialog.show();


                } else if (btn == 1) {
                    String kataid = Integer.toString(id);

                    List<list_item_obat> item = database.getItems(kataid, "id");
                    int idobat = item.get(0).getId();
                    String namaobat = item.get(0).getNama();
                    String jenisobat = item.get(0).getJenis();
                    String hargaobat = item.get(0).getHarga();
                    String jmlobat = item.get(0).getJmobat();
                    String desobat = item.get(0).getDeskripsi();
                    String masaobat = item.get(0).getKadaluarsa();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Result");
                    builder.setMessage("ID Obat: " + idobat + "\n " +
                            "Nama Obat: " + namaobat + "\n " +
                            "Jenis Obat: " + jenisobat + "\n " +
                            "Masa Kadaluarsa: " + masaobat + "\n " +
                            "Harga Obat: " + hargaobat + "\n " +
                            "Jumlah Obat: " + jmlobat + "\n " +
                            "Keterangan: " + desobat);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
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


    private void scanCode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        barlauncher.launch(scanOptions);

    }
    ActivityResultLauncher<ScanOptions> barlauncher = registerForActivityResult(new ScanContract(), result -> {
    if (result.getContents() != null){
        List<list_item_obat> qritem = database.getItems(result.getContents().toString(), "id");
        String namaobat = qritem.get(0).getNama();
        String jenisobat = qritem.get(0).getJenis();
        String expobat = qritem.get(0).getKadaluarsa();
        String hargaobat = qritem.get(0).getHarga();
        String jmlobat = qritem.get(0).getJmobat();
        String desobat = qritem.get(0).getDeskripsi();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Result");
        if (namaobat.equals("")){
            builder.setMessage("Obat Tidak Terdeteksi");
        }else {
            builder.setMessage("Nama Obat: " + namaobat + "\n " +
                    "Jenis Obat: " + jenisobat + "\n " +
                    "Masa Kadaluarsa: " + expobat + "\n " +
                    "Harga Obat: " + hargaobat + "\n " +
                    "Jumlah Obat: " + jmlobat + "\n " +
                    "Keterangan: " + desobat);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    });


}

