package com.example.projek_uas_rivan;

public class Cookies {
    private static Cookies instance;
    private int id;

    private Cookies() {

        id = 0;
    }

    public static Cookies getInstance() {
        if (instance == null) {
            synchronized (Cookies.class) {
                if (instance == null) {
                    instance = new Cookies();
                }
            }
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


