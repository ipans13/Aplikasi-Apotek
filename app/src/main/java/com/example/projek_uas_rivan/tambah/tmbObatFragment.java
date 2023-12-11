package com.example.projek_uas_rivan.tambah;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projek_uas_rivan.Database;
import com.example.projek_uas_rivan.MainActivity;
import com.example.projek_uas_rivan.R;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class tmbObatFragment extends Fragment {

    final Calendar Kalender = Calendar.getInstance();

    Database database;
    EditText kadaluarsa, nmobat, expobat, hrgobat, jmobat, desobat;
    Button tmbobat;


    Spinner jenis,jenobat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tmb_obat, container, false);
        database = new Database(getActivity());
        view.setPadding(0, 100, 0, 0);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.setLayoutParams(layoutParams);
        jenis = (Spinner) view.findViewById(R.id.spinner_Jenis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Jenis, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        jenis.setAdapter(adapter);
        kadaluarsa = view.findViewById(R.id.text_kadaluarsa);
        kadaluarsa.setOnClickListener(v -> {
            selectkalender();
        });
        nmobat = view.findViewById(R.id.Text_obat);
        expobat = view.findViewById(R.id.text_kadaluarsa);
        hrgobat = view.findViewById(R.id.text_harga);
        desobat = view.findViewById(R.id.text_desk);
        jmobat = view.findViewById(R.id.Text_jumlah);
        jenobat = view.findViewById(R.id.spinner_Jenis);
        tmbobat = view.findViewById(R.id.btntmb_obat);
        tmbobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nmobat.getText().toString().isEmpty() || expobat.getText().toString().isEmpty() || hrgobat.getText().toString().isEmpty() || desobat.getText().toString().isEmpty() || jmobat.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Lengkapi semua data yang diperlukan", Toast.LENGTH_SHORT);
                }else {
                    String pesan = database.addatabase(nmobat.getText().toString(), jenobat.getSelectedItem().toString(), expobat.getText().toString(), hrgobat.getText().toString(), desobat.getText().toString(), jmobat.getText().toString());
                    showWarningDialog(pesan);
                    nmobat.setText("");
                    expobat.setText("");
                    hrgobat.setText("");
                    desobat.setText("");
                    jmobat.setText("");
                }
            }
        });

        return view;
    }


    private void showWarningDialog( String isi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pesan");
        builder.setMessage(isi);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
        private void selectkalender(){
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Kalender.set(Calendar.DAY_OF_MONTH,day);
                    Kalender.set(Calendar.MONTH, month);
                    Kalender.set(Calendar.YEAR, year);
                    kadaluarsa.setText(updateTanggal());

                }

            };
            kadaluarsa.setOnClickListener(new View.OnClickListener() {
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
