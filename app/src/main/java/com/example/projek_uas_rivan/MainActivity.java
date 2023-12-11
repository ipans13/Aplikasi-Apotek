package com.example.projek_uas_rivan;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.projek_uas_rivan.home.HomeFragment;
import com.example.projek_uas_rivan.list.daftarObatFragment;
import com.example.projek_uas_rivan.tambah.tmbObatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.karumi.dexter.Dexter;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    LinearLayout linearLayout;
    HomeFragment homeFragment = new HomeFragment();
    com.example.projek_uas_rivan.list.daftarObatFragment daftarObatFragment = new daftarObatFragment();
    com.example.projek_uas_rivan.tambah.tmbObatFragment tmbObatFragment = new tmbObatFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.nav_DaftarObat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,daftarObatFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.nav_Tambah) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,tmbObatFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }
}