package com.example.projek_uas_rivan.home;

public class list_pradakaluarsa_obat {
    String nama, jenis,kadaluarsa, deskripsi, harga,jmobat;

    int id,image;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJmobat() {
        return jmobat;
    }

    public void setJmobat(String jmobat) {
        this.jmobat = jmobat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public list_pradakaluarsa_obat(int id, String nama, String jenis, String kadaluarsa, String deskripsi, String harga, String jmobat,int image){
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.kadaluarsa = kadaluarsa;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.jmobat = jmobat;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
