package com.ocak.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public void soruOlustur(View view){

        Intent intent = new  Intent(this,SoruOlusturActivity.class);
        startActivity(intent);

    }


    public void soruListesineGit(View view){

        Intent intent = new  Intent(this,SoruListesiActivity.class);
        startActivity(intent);

    }


    public void ayarYap(View view){

        Intent intent = new Intent(this,SoruAyarActivity.class);
        startActivity(intent);

    }


    public void sinavOlustur(View view){

        Intent intent = new Intent(this,SinavOlusturActivity.class);
        startActivity(intent);

    }



}