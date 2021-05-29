package com.ocak.ders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;



public class SinavOlusturActivity extends AppCompatActivity {

    ArrayList<Soru> soruListesi;


    private RecyclerView recyclerView;
    private RecyclerAdapterSinavSoruListesi adapter;
    private RecyclerView.LayoutManager layoutManager;

    int sinavPuani, sinavSuresi, sinavZorluk;
    String zorlukBilgisi;

    FileOutputStream oStream;

    SeekBar puanDegisim, sureDegisim;
    TextView puanDegisimText, sureDegisimText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav_olustur);


        SharedPreferences sharedPref = this.getSharedPreferences("SoruAyarlari", Context.MODE_PRIVATE);
        sinavPuani = sharedPref.getInt("soruPuani", 0);
        sinavSuresi = sharedPref.getInt("sure", 0);
        sinavZorluk = sharedPref.getInt("sinavZorluk", 0);
        zorlukBilgisi = String.valueOf(sinavZorluk);


        recyclerView = findViewById(R.id.recyclerViewSorular);
        puanDegisim = findViewById(R.id.soruPuaniDegistirAyari);
        puanDegisimText = findViewById(R.id.soruPuaniDegistirText);
        sureDegisim = findViewById(R.id.sinavSuresiDegistirAyari);
        sureDegisimText = findViewById(R.id.sinavSuresiDegistirText);

        puanDegisim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sinavPuani=progress;
                puanDegisimText.setText("Sınav sorusu puanı:"+" "+String.valueOf(progress)+" "+"puan");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sureDegisim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sinavSuresi=progress;
                sureDegisimText.setText("Sınav süresi:"+" "+String.valueOf(progress)+" "+"dk");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        soruListesi = new ArrayList<>();


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterSinavSoruListesi(soruListesi);
        recyclerView.setAdapter(adapter);

        sqldenverilerifiltreligetir();


    }


    public void sqldenverilerifiltreligetir() {

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Sorular", Context.MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM sorular", null);
            Integer soruIdIndex = cursor.getColumnIndex("id");
            Integer soruIndex = cursor.getColumnIndex("soru");
            Integer aIndex = cursor.getColumnIndex("a");
            Integer bIndex = cursor.getColumnIndex("b");
            Integer cIndex = cursor.getColumnIndex("c");
            Integer dIndex = cursor.getColumnIndex("d");
            Integer cevapIndex = cursor.getColumnIndex("cevap");
            Integer zorlukIndex = cursor.getColumnIndex("zorluk");

            soruListesi.clear();

            while (cursor.moveToNext()) {

                Integer id = cursor.getInt(soruIdIndex);
                String soru = cursor.getString(soruIndex);
                String a = cursor.getString(aIndex);
                String b = cursor.getString(bIndex);
                String c = cursor.getString(cIndex);
                String d = cursor.getString(dIndex);
                String cevap = cursor.getString(cevapIndex);
                String zorluk = cursor.getString(zorlukIndex);
                String zorlukSeviyesiBilgisi = "Soru zorluk seviyesi:" + " " + zorluk;
                String cevapBilgisi = "Doğru Seçenek:" + " " + cevap;
                String aBilgisi = "A)" + " " + a;
                String bBilgisi = "B)" + " " + b;
                String cBilgisi = "C)" + " " + c;
                String dBilgisi = "D)" + " " + d;


                if (zorluk.equals(zorlukBilgisi)) {

                    Soru indirilen = new Soru(id, soru, aBilgisi, bBilgisi, cBilgisi, dBilgisi, cevapBilgisi, zorlukSeviyesiBilgisi);
                    soruListesi.add(indirilen);

                }


            }

            adapter.notifyDataSetChanged();


            cursor.close();

        } catch (Exception e) {


            e.printStackTrace();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sinav_olustur, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.sinavYap) {


            if (adapter.getDosya() != "") {


                String sure = String.valueOf(sinavSuresi);
                String puan = String.valueOf(sinavPuani);

                String yazilacakDosya = "Sınav süresi(dk):" + " " + sure + " " + "Sınav sorusu puanı:" + " " + puan + " " + adapter.getDosya();

                try {
                    oStream = openFileOutput("dosya.txt", Context.MODE_PRIVATE);
                } catch (Exception e) {
                    System.out.println(e);
                }
                try {
                    oStream.write(yazilacakDosya.getBytes());
                    oStream.close();
                    Toast.makeText(this, getFilesDir() + "/" + "dosya.txt" + " " + "dizini oluştu.", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                }


                Uri path = FileProvider.getUriForFile(this, "com.ocak.ders", new File(getFilesDir() + "/" + "dosya.txt"));
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_SUBJECT, "Sınav");
                i.putExtra(Intent.EXTRA_TEXT, "Dosya ektedir.");
                i.putExtra(Intent.EXTRA_STREAM, path);
                i.setType("text/*");
                startActivity(Intent.createChooser(i, "E-mail gönder..."));


            }else {
                Toast.makeText(this,"Soru seçilmeden sınav oluşturulamaz!",Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

}