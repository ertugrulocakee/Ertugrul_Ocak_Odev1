package com.ocak.ders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SoruListesiActivity extends AppCompatActivity {


    ArrayList<Soru> soruListesi;
    SwipeRefreshLayout refresh;

    private RecyclerView recyclerView;
    private  RecyclerAdapterSoruListesi adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_listesi);

        recyclerView = findViewById(R.id.recyclerView);
        soruListesi = new ArrayList<>();
        refresh = findViewById(R.id.swipeRefreshLayout);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterSoruListesi(soruListesi);
        recyclerView.setAdapter(adapter);

        sqldenverilerigetir();


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                sqldenverilerigetir();

                refresh.setRefreshing(false);
            }
        });




    }




     public void sqldenverilerigetir(){

        try{

            SQLiteDatabase database = this.openOrCreateDatabase("Sorular",Context.MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM sorular",null);
            Integer soruIdIndex = cursor.getColumnIndex("id");
            Integer soruIndex = cursor.getColumnIndex("soru");
            Integer aIndex = cursor.getColumnIndex("a");
            Integer bIndex = cursor.getColumnIndex("b");
            Integer cIndex = cursor.getColumnIndex("c");
            Integer dIndex = cursor.getColumnIndex("d");
            Integer cevapIndex = cursor.getColumnIndex("cevap");
            Integer zorlukIndex = cursor.getColumnIndex("zorluk");

              soruListesi.clear();

            while ( cursor.moveToNext()){

                Integer id = cursor.getInt(soruIdIndex);
                String soru = cursor.getString(soruIndex);
                String a = cursor.getString(aIndex);
                String b = cursor.getString(bIndex);
                String c = cursor.getString(cIndex);
                String d = cursor.getString(dIndex);
                String cevap = cursor.getString(cevapIndex);
                String zorluk = cursor.getString(zorlukIndex);
                String zorlukSeviyesiBilgisi="Soru zorluk seviyesi:"+" "+zorluk;
                String cevapBilgisi="Doğru Seçenek:"+" "+cevap;
                String aBilgisi="A)"+" "+a;
                String bBilgisi="B)"+" "+b;
                String cBilgisi="C)"+" "+c;
                String dBilgisi="D)"+" "+d;


              Soru indirilen = new Soru(id,soru,aBilgisi,bBilgisi,cBilgisi,dBilgisi,cevapBilgisi,zorlukSeviyesiBilgisi);
              soruListesi.add(indirilen);

            }

             adapter.notifyDataSetChanged();


            cursor.close();

        }catch (Exception e){


          e.printStackTrace();


        }

     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.soru_ekle,menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.soruEkle){

            Intent intent = new Intent(this,SoruOlusturActivity.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }


}