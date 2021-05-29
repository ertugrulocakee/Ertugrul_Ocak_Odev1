package com.ocak.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SoruAyarActivity extends AppCompatActivity {

    TextView puanBilgisi ,sinavSuresiBilgisi,sinavZorlukDuzeyi;
    SeekBar puanAyari, sinavSuresiAyari,sinavZorlukAyari;
    int soruPuani,sinavSuresi,sinavZorlukSeviyesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_ayar);

        puanBilgisi = findViewById(R.id.soruPuaniText);
        sinavSuresiBilgisi = findViewById(R.id.sinavSuresiText);
        puanAyari = findViewById(R.id.soruPuaniAyari);
        sinavSuresiAyari = findViewById(R.id.sinavSuresiAyari);
        sinavZorlukDuzeyi = findViewById(R.id.sinavZorlukDuzeyi);
        sinavZorlukAyari = findViewById(R.id.sinavZorlukAyari);

       puanAyari.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               soruPuani=progress;
               puanBilgisi.setText("Sınav sorusu puanı:"+" "+String.valueOf(progress)+" "+"puan");
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });

      sinavSuresiAyari.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              sinavSuresi=progress;
              sinavSuresiBilgisi.setText("Sınav süresi:"+" "+String.valueOf(progress)+" "+"dk");
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }
      });

      sinavZorlukAyari.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              sinavZorlukSeviyesi=progress;
              sinavZorlukDuzeyi.setText("Sınavın zorluk seviyesi:"+" "+String.valueOf(progress));
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }
      });

    }



    public void kaydet(View view){


        SharedPreferences ayarlar = getSharedPreferences("SoruAyarlari", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ayarlar.edit();

        editor.putInt("soruPuani",soruPuani);
        editor.putInt("sinavZorluk",sinavZorlukSeviyesi);
        editor.putInt("sure",sinavSuresi);

        editor.commit();

        Toast.makeText(this,"Sınav ayarları oluşturuldu/güncellendi.",Toast.LENGTH_LONG).show();

    }






}