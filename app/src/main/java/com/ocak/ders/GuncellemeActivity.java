package com.ocak.ders;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GuncellemeActivity extends AppCompatActivity {

    EditText soruIki;
    TextView aIki;
    TextView bIki;
    TextView cIki;
    TextView dIki;
    Integer id;
    String kimlikNumarasi;

    RadioGroup soruAlaniIki,soruZorlukAlaniIki;
    RadioButton aSecenegiIki,bSecenegiIki,cSecenegiIki,dSecenegiIki,birSeviyesiIki,ikiSeviyesiIki,ucSeviyesiIki,dortSeviyesiIki,besSeviyesiIki;

    String dogruSenecekIki = "";
    String zorlukSeviyesiIki = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelleme);


        soruIki = findViewById(R.id.soruTextIki);

        aIki = findViewById(R.id.secenekTextBirIki);
        bIki = findViewById(R.id.secenekTextIkiIki);
        cIki = findViewById(R.id.secenekTextUcIki);
        dIki = findViewById(R.id.secenekTextDortIki);
        soruAlaniIki =findViewById(R.id.dogruCevapAlaniIki);
        soruZorlukAlaniIki=findViewById(R.id.soruZorlukAlaniIki);
        aSecenegiIki=findViewById(R.id.aButonuIki);
        bSecenegiIki=findViewById(R.id.bButonuIki);
        cSecenegiIki=findViewById(R.id.cButonuIki);
        dSecenegiIki=findViewById(R.id.dButonuIki);
        birSeviyesiIki=findViewById(R.id.birButonuIki);
        ikiSeviyesiIki=findViewById(R.id.ikiButonuIki);
        ucSeviyesiIki=findViewById(R.id.ucButonuIki);
        dortSeviyesiIki=findViewById(R.id.dortButonuIki);
        besSeviyesiIki=findViewById(R.id.besButonuIki);


        id = getIntent().getIntExtra("id",0);
        kimlikNumarasi=id.toString();



    }


    public void soruMetniniGuncelle(View view) {

        String soruMetni = soruIki.getText().toString();



        if (!soruMetni.equals("")) {

            try{

                SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
                String sqlString="UPDATE sorular SET soru = ?  WHERE id = ?";
                SQLiteStatement statement = veritabani.compileStatement(sqlString);
                statement.bindString(1,soruMetni);
                statement.bindString(2,kimlikNumarasi);
                statement.execute();

                Toast.makeText(this,"Soru metni başarıyla güncellendi.",Toast.LENGTH_LONG).show();




            }catch (Exception e){
                e.printStackTrace();
            }




        }else{

            Toast.makeText(this,"Lütfen bir soru metni giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }



    }

    public void aSeceneginiGuncelle(View view){

        String aSecenegi=aIki.getText().toString();

        if(!aSecenegi.equals("")){

            try{

                SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
                String sqlString="UPDATE sorular SET a = ? WHERE id = ?";
                SQLiteStatement statement = veritabani.compileStatement(sqlString);
                statement.bindString(1,aSecenegi);
                statement.bindString(2,kimlikNumarasi);
                statement.execute();

                Toast.makeText(this,"A seçeneği başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                e.printStackTrace();
            }




        }else{

            Toast.makeText(this,"Lütfen A seçeneğini tanımlayın!",Toast.LENGTH_SHORT).show();
            return;

        }


    }


    public void bSeceneginiGuncelle(View view){


        String bSecenegi=bIki.getText().toString();

        if(!bSecenegi.equals("")){

            try{

                SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
                String sqlString="UPDATE sorular SET b = ? WHERE id = ?";
                SQLiteStatement statement = veritabani.compileStatement(sqlString);
                statement.bindString(1,bSecenegi);
                statement.bindString(2,kimlikNumarasi);
                statement.execute();

                Toast.makeText(this,"B seçeneği başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                e.printStackTrace();
            }




        }else{

            Toast.makeText(this,"Lütfen B seçeneğini tanımlayın!",Toast.LENGTH_SHORT).show();
            return;

        }



    }

   public void cSeceneginiGuncelle(View view){


       String cSecenegi=cIki.getText().toString();

       if(!cSecenegi.equals("")){

           try{

               SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
               String sqlString="UPDATE sorular SET c = ? WHERE id = ?";
               SQLiteStatement statement = veritabani.compileStatement(sqlString);
               statement.bindString(1,cSecenegi);
               statement.bindString(2,kimlikNumarasi);
               statement.execute();

               Toast.makeText(this,"C seçeneği başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


           }catch (Exception e){
               e.printStackTrace();
           }




       }else{

           Toast.makeText(this,"Lütfen C seçeneğini tanımlayın!",Toast.LENGTH_SHORT).show();
           return;

       }



   }


   public void dSeceneginiGuncelle(View view){


       String dSecenegi=dIki.getText().toString();

       if(!dSecenegi.equals("")){

           try{

               SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
               String sqlString="UPDATE sorular SET d = ? WHERE id = ?";
               SQLiteStatement statement = veritabani.compileStatement(sqlString);
               statement.bindString(1,dSecenegi);
               statement.bindString(2,kimlikNumarasi);
               statement.execute();

               Toast.makeText(this,"D seçeneği başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


           }catch (Exception e){
               e.printStackTrace();
           }




       }else{

           Toast.makeText(this,"Lütfen D seçeneğini tanımlayın!",Toast.LENGTH_SHORT).show();
           return;

       }




   }

   public void dogruSecenegiGuncelle(View view){


       if(dogruSenecekIki != ""){

           try{

               SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
               String sqlString="UPDATE sorular SET cevap = ? WHERE id = ?";
               SQLiteStatement statement = veritabani.compileStatement(sqlString);
               statement.bindString(1,dogruSenecekIki);
               statement.bindString(2,kimlikNumarasi);
               statement.execute();

               Toast.makeText(this,"Doğru seçenek başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


           }catch (Exception e){
               e.printStackTrace();
           }




       }else{

           Toast.makeText(this,"Lütfen doğru seçeneği tanımlayın!",Toast.LENGTH_SHORT).show();
           return;

       }




   }


   public void zorlukSeviyesiniGuncelle(View view){



       if(zorlukSeviyesiIki != ""){

           try{

               SQLiteDatabase veritabani= this.openOrCreateDatabase("Sorular", MODE_PRIVATE,null);
               String sqlString="UPDATE sorular SET zorluk = ? WHERE id = ?";
               SQLiteStatement statement = veritabani.compileStatement(sqlString);
               statement.bindString(1,zorlukSeviyesiIki);
               statement.bindString(2,kimlikNumarasi);
               statement.execute();

               Toast.makeText(this,"Zorluk seviyesi başarıyla güncellendi.",Toast.LENGTH_SHORT).show();


           }catch (Exception e){
               e.printStackTrace();
           }




       }else{

           Toast.makeText(this,"Zorluk seviyesini tanımlayın!",Toast.LENGTH_SHORT).show();
           return;

       }




   }


   public void listeyeDon(View view){


        Intent intent = new Intent(this,SoruListesiActivity.class);
        startActivity(intent);
        finish();

   }


    public void dogruyuSec(View view){

        int radioId = soruAlaniIki.getCheckedRadioButtonId();

        if (radioId == aSecenegiIki.getId()){

            dogruSenecekIki = "A";

        }

        else if (radioId == bSecenegiIki.getId()){

            dogruSenecekIki = "B";
        }

        else if (radioId == cSecenegiIki.getId()){

            dogruSenecekIki = "C";
        }

        else if (radioId == dSecenegiIki.getId()){

            dogruSenecekIki= "D";
        }
        else{
            dogruSenecekIki= "";
        }


    }

    public  void zorlukSec(View view){

        int radioId = soruZorlukAlaniIki.getCheckedRadioButtonId();

        if (radioId==birSeviyesiIki.getId()){

            zorlukSeviyesiIki="1";

        }else if(radioId==ikiSeviyesiIki.getId()){

            zorlukSeviyesiIki="2";

        }else if(radioId==ucSeviyesiIki.getId()){
            zorlukSeviyesiIki="3";

        }else if(radioId==dortSeviyesiIki.getId()){
            zorlukSeviyesiIki="4";

        }else if(radioId==besSeviyesiIki.getId()){
            zorlukSeviyesiIki="5";

        }else{
            zorlukSeviyesiIki="";
        }




    }








}