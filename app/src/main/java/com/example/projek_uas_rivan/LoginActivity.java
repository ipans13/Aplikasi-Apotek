package com.example.projek_uas_rivan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    Cookies cookies;
    Database database;
    EditText user, password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cookies = Cookies.getInstance();

        user = findViewById(R.id.text_login);
        password = findViewById(R.id.text_password);
        database = new Database(this);

        if(cekid()){
            Intent beranda = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(beranda);
        }
        Button login = findViewById(R.id.Sign_in);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Lengkapi Data yang diperlukan", Toast.LENGTH_SHORT).show();
                }else {
                    int cek = database.ceklogin(user.getText().toString(),password.getText().toString());
                    if(cek != 0){
                         cookies.setId(cek);
                        Intent beranda = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(beranda);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this,"Masukkan Identitas yang Valid", Toast.LENGTH_SHORT).show();
                    }
                }

                }

        });
    }
    private Boolean cekid(){
        Boolean status = false;
        int id = cookies.getId();
        int cek = database.getuser(id);
        if(cek != 0){
            status = true;
        }
        return  status;
    }
}

