package com.ocak.ders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class RecyclerAdapterSoruListesi  extends  RecyclerView.Adapter<RecyclerAdapterSoruListesi.SoruViewHolder> {

     private ArrayList<Soru> soruListesi;




    public RecyclerAdapterSoruListesi(ArrayList<Soru> soruListesi) {

        this.soruListesi = soruListesi;

    }



    public  class SoruViewHolder extends RecyclerView.ViewHolder {


        TextView soru ,a,b,c,d,cevap,zorlukSeviyesi ;
        Button sil;
        Button guncelle;


        public SoruViewHolder(@NonNull View itemView) {
            super(itemView);


            soru  = itemView.findViewById(R.id.recycler_soru);
            a =itemView.findViewById(R.id.recycler_a);
            b =itemView.findViewById(R.id.recycler_b);
            c = itemView.findViewById(R.id.recycler_c);
            d = itemView.findViewById(R.id.recycler_d);
            cevap =itemView.findViewById(R.id.recycler_cevap);
            zorlukSeviyesi = itemView.findViewById(R.id.recycler_zorluk);
            sil = itemView.findViewById(R.id.recycler_silme);
            guncelle = itemView.findViewById(R.id.recycler_guncelleme);

        }

    }





    @NonNull
     @Override
     public SoruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View view = layoutInflater.inflate(R.layout.recycler_row_soru,parent,false);

         SoruViewHolder vh = new SoruViewHolder(view);

         return  vh;
     }


     @Override
     public void onBindViewHolder(@NonNull SoruViewHolder holder, int position) {

         holder.soru.setText(soruListesi.get(position).soru);
         holder.a.setText(soruListesi.get(position).a);
         holder.b.setText(soruListesi.get(position).b);
         holder.c.setText(soruListesi.get(position).c);
         holder.d.setText(soruListesi.get(position).d);
         holder.cevap.setText(soruListesi.get(position).cevap);
         holder.zorlukSeviyesi.setText(soruListesi.get(position).zorlukSeviyesi);


         holder.sil.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder builderSil = new AlertDialog.Builder(holder.itemView.getContext());
               builderSil.setMessage("Gerçekten bu soruyu silmek istiyor musun?");
               builderSil.setCancelable(true);
               builderSil.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       String id = soruListesi.get(position).id.toString();


                       try {

                           SQLiteDatabase veritabani = holder.itemView.getContext().openOrCreateDatabase("Sorular", Context.MODE_PRIVATE,null);
                           String sqlString ="DELETE FROM sorular WHERE id = ?";
                           SQLiteStatement sqLiteStatement = veritabani.compileStatement(sqlString);
                           sqLiteStatement.bindString(1,id);
                           sqLiteStatement.execute();

                           Toast.makeText(holder.itemView.getContext(),"Seçilen soru silindi!",Toast.LENGTH_LONG).show();



                       }catch (Exception e){

                           e.printStackTrace();

                       }


                   }
               });

            builderSil.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            AlertDialog alert = builderSil.create();
            alert.show();


           }

       });

       holder.guncelle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent= new Intent(holder.itemView.getContext(),GuncellemeActivity.class);
               intent.putExtra("id",soruListesi.get(position).id);
               holder.itemView.getContext().startActivity(intent);
           }
       });


     }

     @Override
     public int getItemCount() {
         return soruListesi.size();
     }





}
