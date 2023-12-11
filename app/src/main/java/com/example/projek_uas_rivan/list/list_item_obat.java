package com.example.projek_uas_rivan.list;

import android.view.View;

public class list_item_obat {
    String nama, jenis,kadaluarsa, deskripsi,jmobat, harga;

    int image,id ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmobat() {
        return jmobat;
    }

    public void setJmobat(String jmobat) {
        this.jmobat = jmobat;
    }

    public list_item_obat(int id, String nama, String jenis, String kadaluarsa, String deskripsi, String harga, String jmobat,int image){
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.kadaluarsa = kadaluarsa;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.jmobat = jmobat;
        this.image = image;

//        Comparator<list_item_obat> coba = new Comparator<list_item_obat>() {
//            @Override
//            public int compare(list_item_obat list_item_obat, list_item_obat t1) {
//                return list_item_obat.getNama().compareTo(t1.nama);
//            }
//        };
    }

    public int getImage() {
        return image;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }


    public String getKadaluarsa() {
        return kadaluarsa;
    }

    public void setKadaluarsa(String kadaluarsa) {
        this.kadaluarsa = kadaluarsa;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


}
