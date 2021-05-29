package com.ocak.ders;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Permission;
import java.util.ArrayList;

public class SoruOlusturActivity extends AppCompatActivity {


    EditText soru;

    TextView a;
    TextView b;
    TextView c;
    TextView d;

    RadioGroup soruAlani,soruZorlukAlani;
    RadioButton aSecenegi,bSecenegi,cSecenegi,dSecenegi,birSeviyesi,ikiSeviyesi,ucSeviyesi,dortSeviyesi,besSeviyesi;

    String dogruSenecek= "";
    String zorlukSeviyesi="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_olustur);


        soru = findViewById(R.id.soruText);

        a = findViewById(R.id.secenekTextBir);
        b = findViewById(R.id.secenekTextIki);
        c = findViewById(R.id.secenekTextUc);
        d = findViewById(R.id.secenekTextDort);
        soruAlani=findViewById(R.id.dogruCevapAlani);
        soruZorlukAlani=findViewById(R.id.soruZorlukAlani);
        aSecenegi=findViewById(R.id.aButonu);
        bSecenegi=findViewById(R.id.bButonu);
        cSecenegi=findViewById(R.id.cButonu);
        dSecenegi=findViewById(R.id.dButonu);
        birSeviyesi=findViewById(R.id.birButonu);
        ikiSeviyesi=findViewById(R.id.ikiButonu);
        ucSeviyesi=findViewById(R.id.ucButonu);
        dortSeviyesi=findViewById(R.id.dortButonu);
        besSeviyesi=findViewById(R.id.besButonu);





    }


    public void soruYap(View view) {

        String soruMetni = soru.getText().toString();
        String aSecenegi = a.getText().toString();
        String bSecenegi = b.getText().toString();
        String cSecenegi = c.getText().toString();
        String dSecenegi = d.getText().toString();


        if (!soruMetni.equals("")){

            if (!aSecenegi.equals("") && !bSecenegi.equals("") && !cSecenegi.equals("") && !dSecenegi.equals("") ){

               if (dogruSenecek!=""){


                   if (zorlukSeviyesi!=""){


                       sorulariSqliteyeCek(soruMetni,aSecenegi,bSecenegi,cSecenegi,dSecenegi,dogruSenecek,zorlukSeviyesi);


                   }else{

                       Toast.makeText(this,"Lütfen bir zorluk seviyesi tanımlayın!",Toast.LENGTH_SHORT).show();
                       return;
                   }


               }else{
                   Toast.makeText(this,"Lütfen bir doğru cevap tanımlayın!",Toast.LENGTH_SHORT).show();
                   return;

               }


            }else{

                Toast.makeText(this,"Lütfen 4 seçenek için de cevap tanımlayın!",Toast.LENGTH_SHORT).show();
                return;

            }


        }else{

            Toast.makeText(this,"Lütfen bir soru giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }



    }



    public void sorulariSqliteyeCek(String soruMetni, String aSecenegi,  String bSecenegi,  String cSecenegi,  String dSecenegi, String cevapBilgisi, String zorlukSeviyesi){


        try{

            SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS sorular (id INTEGER PRIMARY KEY ,soru VARCHAR , a VARCHAR ,  b VARCHAR ,  c VARCHAR , d VARCHAR , cevap VARCHAR , zorluk VARCHAR )");
            String sqlString=  "INSERT INTO sorular (soru,a,b,c,d,cevap,zorluk) VALUES (?,?,?,?,?,?,?)";

            SQLiteStatement statement = veritabani.compileStatement(sqlString);
            statement.bindString(1,soruMetni);
            statement.bindString(2,aSecenegi);
            statement.bindString(3,bSecenegi);
            statement.bindString(4,cSecenegi);
            statement.bindString(5,dSecenegi);
            statement.bindString(6,cevapBilgisi);
            statement.bindString(7,zorlukSeviyesi);
            statement.execute();

            Toast.makeText(this,"Soru veritabanına kaydedildi.",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this,SoruListesiActivity.class);
            startActivity(intent);
            finish();


        }catch (Exception e){
            e.printStackTrace();
        }




    }



    public void sec(View view){

       int radioId = soruAlani.getCheckedRadioButtonId();

       if (radioId == aSecenegi.getId()){

           dogruSenecek = "A";

       }

       else if (radioId == bSecenegi.getId()){

           dogruSenecek = "B";
       }

       else if (radioId == cSecenegi.getId()){

           dogruSenecek = "C";
       }

       else if (radioId == dSecenegi.getId()){

           dogruSenecek= "D";
       }
       else{
           dogruSenecek= "";
       }


    }

    public  void zorlukSec(View view){

        int radioId = soruZorlukAlani.getCheckedRadioButtonId();

        if (radioId==birSeviyesi.getId()){

            zorlukSeviyesi="1";

        }else if(radioId==ikiSeviyesi.getId()){

            zorlukSeviyesi="2";

        }else if(radioId==ucSeviyesi.getId()){
            zorlukSeviyesi="3";

        }else if(radioId==dortSeviyesi.getId()){
            zorlukSeviyesi="4";

        }else if(radioId==besSeviyesi.getId()){
            zorlukSeviyesi="5";

        }else{
            zorlukSeviyesi="";
        }




    }





    }



