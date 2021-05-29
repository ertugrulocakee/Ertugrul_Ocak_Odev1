package com.ocak.ders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KayitActivity extends AppCompatActivity {

    Uri uri;
    Bitmap secilenGorsel;

    EditText kullaniciIsimText, kullaniciSoyadText ,kullaniciSifreText, kullaniciSifreIkiText , kullaniciEmailText , kullaniciTelefonText;
    Button kayitButon;
    ImageView kullaniciProfilResmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        kullaniciIsimText = findViewById(R.id.kullaniciIsimText);
        kullaniciSoyadText = findViewById(R.id.kullanıcıSoyadiText);
        kullaniciSifreText = findViewById(R.id.kullaniciPasswordText);
        kullaniciSifreIkiText = findViewById(R.id.kullaniciPasswordTwoText);
        kullaniciEmailText = findViewById(R.id.kullaniciEmailText);
        kullaniciTelefonText = findViewById(R.id.kullaniciTelefonText);
        kullaniciProfilResmi = findViewById(R.id.kullaniciResim);

        kayitButon = findViewById(R.id.buttonKayit);


    }



    public void kayitOl(View view){

        String kullaniciAdi = kullaniciIsimText.getText().toString();
        String kullaniciSoyadi = kullaniciSoyadText.getText().toString();
        String kullaniciSifre = kullaniciSifreText.getText().toString();
        String kullaniciSifreIki = kullaniciSifreIkiText.getText().toString();
        String kullaniciEmail = kullaniciEmailText.getText().toString();
        String kullaniciTelefon = kullaniciTelefonText.getText().toString();
        String kullanici=kullaniciAdi+" "+kullaniciSoyadi;




        if (!kullaniciAdi.equals("") && !kullaniciSifre.equals("") && !kullaniciSoyadi.equals("") && !kullaniciSifreIki.equals("") && !kullaniciEmail.equals("") && !kullaniciTelefon.equals("") && kullaniciProfilResmi != null) {



            if(!kullaniciSifre.equals(kullaniciSifreIki)){
                Toast.makeText(this,"Şifreniz uyuşmamaktadır!",Toast.LENGTH_LONG).show();
                return;
            }

           else if (sqlKullaniciSorgulama(kullanici) == 1) {

                Toast.makeText(this, "Böyle bir kullanıcı adı zaten var!", Toast.LENGTH_LONG).show();
                return;

            }else {

                Bitmap kucukBitmap = kucukProfilResmiBitmapOlustur(secilenGorsel,300);
                ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
                kucukBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                byte[] byteDizisi = outputStream.toByteArray();

                sqlKayitAl(kullanici, kullaniciSifre,kullaniciEmail,kullaniciTelefon,byteDizisi);
                Toast.makeText(this, "Kayıt başarıyla tamamlandı!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        } else {

            Toast.makeText(this, "Boş yer bırakmayınız!", Toast.LENGTH_LONG).show();
            return;

        }

    }



    public void sqlKayitAl(String kullaniciAdi, String kullaniciSifre , String kullaniciEmail ,String kullaniciTelefon , byte[] gorsel){


        try{

            SQLiteDatabase veritabani= this.openOrCreateDatabase("Kullanicilar", MODE_PRIVATE,null);
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS kullanicilar (id INTEGER PRIMARY KEY ,isim VARCHAR , sifre VARCHAR , email VARCHAR , telefon VARCHAR ,gorsel VARCHAR)");
            String sqlString=  "INSERT INTO kullanicilar (isim ,sifre,email,telefon,gorsel ) VALUES (?,?,?,?,?)";

            SQLiteStatement statement = veritabani.compileStatement(sqlString);

            statement.bindString(1,kullaniciAdi);
            statement.bindString(2,kullaniciSifre);
            statement.bindString(3,kullaniciEmail);
            statement.bindString(4,kullaniciTelefon);
            statement.bindBlob(5,gorsel);
            statement.execute();


        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public Integer sqlKullaniciSorgulama(String kullaniciAdi ) {

        Integer durum = 0;

        try {
            SQLiteDatabase veritabani = this.openOrCreateDatabase("Kullanicilar", MODE_PRIVATE, null);
            Cursor cursor = veritabani.rawQuery("SELECT * FROM  kullanicilar", null);

            Integer kullaniciAdiIndex = cursor.getColumnIndex("isim");

            while (cursor.moveToNext()) {

                String kullanici = cursor.getString(kullaniciAdiIndex);


                if (kullaniciAdi.equals(kullanici)) {
                    durum = 1;
                    return durum;

                }
                else{
                    durum=0;
                    return durum;
                }

            }


            cursor.close();


        } catch (Exception e) {

            e.printStackTrace();

        }

        return durum;

    }


    public void profilResmiSec(View view){


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 2 ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode ==1 && resultCode == RESULT_OK && data != null) {

            uri = data.getData();

            try {
                secilenGorsel = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                kullaniciProfilResmi.setImageBitmap(secilenGorsel);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    public Bitmap kucukProfilResmiBitmapOlustur(Bitmap secilenBitmap , Integer maxBoyut ) {


        Integer genislik = secilenBitmap.getWidth();
        Integer boyut = secilenBitmap.getHeight();

        Double bitmapOrani = genislik.doubleValue()/boyut.doubleValue();

        if (bitmapOrani>1){

            genislik=maxBoyut;
            Double kisaltilmisBoyut = genislik/bitmapOrani;
            boyut= kisaltilmisBoyut.intValue();

        }else{

            boyut = maxBoyut;
            Double kisaltilmisGenislik = boyut*bitmapOrani;
            genislik = kisaltilmisGenislik.intValue();

        }




        return Bitmap.createScaledBitmap(secilenBitmap,genislik,boyut,true);

    }




}