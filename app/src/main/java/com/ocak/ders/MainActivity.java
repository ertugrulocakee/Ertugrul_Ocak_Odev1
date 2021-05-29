package com.ocak.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private int count = 0;

    EditText kullaniciAdiText, kullaniciSifreText;
    Button buttonGiris , buttonKayit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kullaniciAdiText= findViewById(R.id.kullaniciGirisText);
        kullaniciSifreText = findViewById(R.id.kullaniciSifreText);
        buttonGiris = findViewById(R.id.buttonGiris);
        buttonKayit = findViewById(R.id.buttonKayit);

    }

    public void girisYap(View view) {



        String kullaniciAdi = kullaniciAdiText.getText().toString();
        String kullaniciSifre = kullaniciSifreText.getText().toString();

        if (!kullaniciAdi.equals("") && !kullaniciSifre.equals("")) {


            try{
                SQLiteDatabase veritabani = this.openOrCreateDatabase("Kullanicilar", MODE_PRIVATE,null);
                Cursor cursor = veritabani.rawQuery("SELECT * FROM  kullanicilar",null);

                int kullaniciAdiIndex = cursor.getColumnIndex("isim");
                int kullaniciSifreIndex = cursor.getColumnIndex("sifre");


                while (cursor.moveToNext()){


                    String kullanici = cursor.getString(kullaniciAdiIndex);
                    String sifre = cursor.getString(kullaniciSifreIndex);




                    if(kullaniciAdi.equals(kullanici) && kullaniciSifre.equals(sifre)){

                         Toast.makeText(this,"Hoşgeldiniz"+" "+kullaniciAdi, Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(this,MenuActivity.class);
                         startActivity(intent);
                         finish();

                    }else{
                        Toast.makeText(this,"Böyle bir hesap kaydı yok ! Lütfen tekrar deneyiniz!",Toast.LENGTH_SHORT).show();
                        count++;
                        if (count == 3) {

                            buttonGiris.setVisibility(View.GONE);
                            Toast.makeText(this,"3 defa yanlış giriş yaptınız!",Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }

                }

                cursor.close();

            }catch (Exception e){

                e.printStackTrace();

            }



        } else {
            Toast.makeText(this, "Boş yer bırakmayınız!", Toast.LENGTH_SHORT).show();
        }


    }


    public void kayit(View view) {

       Intent intent = new Intent(getApplicationContext(),KayitActivity.class);
       startActivity(intent);


    }



}










