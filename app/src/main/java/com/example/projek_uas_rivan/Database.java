package com.example.projek_uas_rivan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projek_uas_rivan.home.list_pradakaluarsa_obat;
import com.example.projek_uas_rivan.list.list_item_obat;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DATABASE_NAME = "masterobat.db";

    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table obat(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_obat TEXT, jenis_obat TEXT, tgl_exp TEXT, hrg_pcs INTEGER, jml INTEGER, desk_obat TEXT)";
        Log.d("DATA", "onCreate:" + sql);
        db.execSQL(sql);
        sql = "create table user(id_user INTEGER PRIMARY KEY, user TEXT,password TEXT,nama_tempat TEXT,koordinat TEXT)";
        db.execSQL(sql);
        db.execSQL("insert into user (id_user, user, password, nama_tempat, koordinat) VALUES ('18290180','Rivan','12345','Apotek Harapan 1','-6.913452, 107.694856')");
        db.execSQL("insert into user (id_user, user, password, nama_tempat, koordinat) VALUES ('19290183','Admin','12345','Apotek Kasih','-6.948211621546349, 107.60111659183501')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Procold','Kaplet', '07-05-2024',4000,200,'Obat flu, demam dan sakit kepala, Membantu meredakan gejala flu, Meredakan nyeri akibat sakit kepala, Menurunkan panas demam Melegakan hidung tersumbat akibat flu')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Promag 1 Box','Kaplet', '30-10-2026',11000,150,'Promag Obat Maag Cair [1 Box] merupakan obat maag dengan kandungan Hydrotalcite 200 mg, untuk mengurangi gejala-gejala yang berhubungan dengan kelebihan asam lambung, gastritis, tukak lambung, nyeri lambung, nyeri ulu hati dan tukak usus dua belas.')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Mixagrip Flu','Cair', '24-08-2027',3000,500,'Membantu meredakan berbagai gejala flu, seperti bersin-bersin, hidung tersumbat, demam dan sakit kepala.')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Caladine','Cair', '20-01-2036',17000,250,'Caladine Lotion adalah salah satu produk yang dihadirkan oleh PT. Galenium Pharmasia Laboratories sebagai produk Antialergi, Antiseptik dan Penyejuk kulit dalam megatasi berbagai masalah gatal di kulit akibat alergi.')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Insto Regular','Tetes', '20-01-2024',14000,20,'obat tetes mata yang mengandung zat aktif Tetrahidrozolin HCl dan Benzalkonium Klorida, digunakan untuk mengatasi mata merah dan rasa perih akibat iritasi mata ringan yang dapat disebabkan karena asap, debu dan lainnya.')");
        db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('Tolaklinu 1 Box','Cair', '10-06-2024',79000,15,'Tolak Linu merupakan herbal untuk membantu meredakan pegal linu dan nyeri sendi yang dibuat dari bahan-bahan herbal alami di pabrik berstandar GMP (Good Manufacturing Practice). Tolak Linu cocok diminum sebelum tidur atau setelah beraktivitas.')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createobat(){

    }
    public List<list_item_obat> getAllItems() {
        List<list_item_obat> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM obat", null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String nama = cursor.getString(1).toString();
                String jenis = cursor.getString(2).toString();
                String tglexp = cursor.getString(3).toString();
                String hrgobt = cursor.getString(4).toString();
                String jmlobt = cursor.getString(5).toString();
                String desobt = cursor.getString(6).toString();
                int gambar = jnsforgmb(jenis);
                list_item_obat item = new list_item_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public List<list_item_obat> getItems(String idget, String cariapa) {
        List<list_item_obat> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM obat WHERE " + cariapa + "=" + idget, null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String nama = cursor.getString(1).toString();
                String jenis = cursor.getString(2).toString();
                String tglexp = cursor.getString(3).toString();
                String hrgobt = cursor.getString(4).toString();
                String jmlobt = cursor.getString(5).toString();
                String desobt = cursor.getString(6).toString();
                int gambar = jnsforgmb(jenis);;
                list_item_obat item = new list_item_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public List<list_pradakaluarsa_obat> getItemshome(String idget, String cariapa) {
        List<list_pradakaluarsa_obat> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM obat WHERE " + cariapa + "=" + idget, null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String nama = cursor.getString(1).toString();
                String jenis = cursor.getString(2).toString();
                String tglexp = cursor.getString(3).toString();
                String hrgobt = cursor.getString(4).toString();
                String jmlobt = cursor.getString(5).toString();
                String desobt = cursor.getString(6).toString();
                int gambar = jnsforgmb(jenis);;
                list_pradakaluarsa_obat item = new list_pradakaluarsa_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public List<list_pradakaluarsa_obat> getItemshome(String idget, String cariapa, String operasi) {
        List<list_pradakaluarsa_obat> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM obat where jml != 0", null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String nama = cursor.getString(1).toString();
                String jenis = cursor.getString(2).toString();
                String tglexp = cursor.getString(3).toString();
                String hrgobt = cursor.getString(4).toString();
                String jmlobt = cursor.getString(5).toString();
                String desobt = cursor.getString(6).toString();
                int gambar = jnsforgmb(jenis);
                int tahuncek = Integer.parseInt(idget.substring(6, 10));
                int tahunex = Integer.parseInt(tglexp.substring(6, 10));
                int bulancek = Integer.parseInt(idget.substring(3, 4));
                int bulanex = Integer.parseInt(tglexp.substring(3, 4));
                if (tahuncek >= tahunex) {
                    int haricek = Integer.parseInt(idget.substring(0, 1));
                    int hariexp = Integer.parseInt(tglexp.substring(0, 1));
                    list_pradakaluarsa_obat item = new list_pradakaluarsa_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                    itemList.add(item);

                }
                if (tahuncek + 1 == tahunex) {
                    int haricek = Integer.parseInt(idget.substring(0, 1));
                    int hariexp = Integer.parseInt(tglexp.substring(0, 1));
                    if (bulanex <= bulancek) {
                        list_pradakaluarsa_obat item = new list_pradakaluarsa_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                        itemList.add(item);
                    }
                }


            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public List<list_item_obat> searchItems(String idget) {
        List<list_item_obat> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM obat WHERE nama_obat LIKE '%" + idget + "%' or tgl_exp LIKE '%" + idget + "%' or hrg_pcs LIKE '%" + idget + "%' or jml LIKE '%" + idget + "%' or desk_obat LIKE '%" + idget + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nama = cursor.getString(1).toString();
                String jenis = cursor.getString(2).toString();
                String tglexp = cursor.getString(3).toString();
                String hrgobt = cursor.getString(4).toString();
                String jmlobt = cursor.getString(5).toString();
                String desobt = cursor.getString(6).toString();
                int gambar = jnsforgmb(jenis);
                list_item_obat item = new list_item_obat(id, nama, jenis, tglexp, desobt, hrgobt, jmlobt, gambar);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public String addatabase(String nmobat, String jenobat, String expobat, String hrgobat, String desobat, String jmobat) {
        db = this.getWritableDatabase();
        String pesan = "";
        int gambar = jnsforgmb(jenobat);
        if (nmobat.isEmpty() || expobat.toString().isEmpty() || hrgobat.isEmpty() || desobat.isEmpty() || jmobat.isEmpty()) {
            pesan = "Lengkapi semua data yang diperlukan";
        } else {
            try {
                db.execSQL("insert into obat (nama_obat, jenis_obat, tgl_exp, hrg_pcs, jml, desk_obat) VALUES ('" +
                        nmobat +
                        "' , '" +
                        jenobat +
                        "' , '" +
                        expobat +
                        "' , '" +
                        hrgobat +
                        "' , '" +
                        jmobat +
                        "' , '" +
                        desobat +
                        "' )");
                pesan = "Data Berhasil Ditambahkan";
            } catch (Exception e) {
                pesan = "Data Gagal Ditambahkan";
            }

        }
        db.close();
        return pesan;
    }

    public String updatedatabase(int id, String nmobat, String jenobat, String expobat, String hrgobat, String desobat, String jmobat) {
        db = this.getWritableDatabase();
        String pesan = "";
        if (nmobat.isEmpty() || expobat.toString().isEmpty() || hrgobat.isEmpty() || desobat.isEmpty() || jmobat.isEmpty()) {
            pesan = "Lengkapi semua data yang diperlukan";
        } else {
            try {
                db.execSQL("update obat SET nama_obat = '" +
                        nmobat +
                        "' , jenis_obat = '" +
                        jenobat +
                        "' , tgl_exp = '" +
                        expobat +
                        "' , hrg_pcs = '" +
                        hrgobat +
                        "' , jml = '" +
                        jmobat +
                        "' , desk_obat = '" +
                        desobat +
                        "' WHERE id= '" + id + "'");
                pesan = "Data Berhasil DiUpdate";
            } catch (Exception e) {
                pesan = "Data Gagal DiUpdate";
            }

        }
        db.close();
        return pesan;
    }

    public String Delobat(int id) {
        db = this.getWritableDatabase();
        String pesan = "";
        String id_obat = Integer.toString(id);
        String[] i = new String[]{id_obat};
        try {
            db.delete("obat", "id = ?", i);
            pesan = "Data Berhasil Dihapus";
        } catch (Exception e) {
            pesan = "Data Gagal Dihapus";
        }

        db.close();
        return pesan;
    }

    public int getjumlahobat(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int getuser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM user WHERE id_user = '" + id + "'", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    public int ceklogin(String user, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user = '" + user + "' AND password= '"+password+"'", null);
        int id = 0;
        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }
    private int jnsforgmb(String jenis){
        int gambar=0;
        String jeniss = jenis.toLowerCase();
        if(jeniss.equals("kaplet")){
            gambar= R.drawable.obat_bubuk;
        } else if (jeniss.equals("tetes")) {
            gambar= R.drawable.obat_cair;
        } else if (jeniss.equals("cair")) {
            gambar= R.drawable.obat_pil;
        } else if (jeniss.equals("kapsul")) {
            gambar= R.drawable.obat_kapsul;
        }
        return gambar;
    }
}

