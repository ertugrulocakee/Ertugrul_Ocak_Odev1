package com.ocak.ders;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterSinavSoruListesi extends  RecyclerView.Adapter<RecyclerAdapterSinavSoruListesi.SinavViewHolder> {

    private ArrayList<Soru> soruListesi;
    private String dosya="";


    public RecyclerAdapterSinavSoruListesi(ArrayList<Soru> soruListesi) {

        this.soruListesi = soruListesi;

    }




    public  class SinavViewHolder extends RecyclerView.ViewHolder {


        TextView soru ,a,b,c,d,cevap,zorlukSeviyesi ;
        Button ekle;



        public SinavViewHolder(@NonNull View itemView) {
            super(itemView);


            soru  = itemView.findViewById(R.id.recycler_soru_sinav);
            a =itemView.findViewById(R.id.recycler_a_sinav);
            b =itemView.findViewById(R.id.recycler_b_sinav);
            c = itemView.findViewById(R.id.recycler_c_sinav);
            d = itemView.findViewById(R.id.recycler_d_sinav);
            cevap =itemView.findViewById(R.id.recycler_cevap_sinav);
            zorlukSeviyesi = itemView.findViewById(R.id.recycler_zorluk_sinav);
            ekle = itemView.findViewById(R.id.recycler_ekle);


        }


    }




    @NonNull
    @Override
    public RecyclerAdapterSinavSoruListesi.SinavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_sinav,parent,false);

        RecyclerAdapterSinavSoruListesi.SinavViewHolder vh = new RecyclerAdapterSinavSoruListesi.SinavViewHolder(view);

        return  vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSinavSoruListesi.SinavViewHolder holder, int position) {

        int secilenSoru = position+1;

        holder.soru.setText(soruListesi.get(position).soru);
        holder.a.setText(soruListesi.get(position).a);
        holder.b.setText(soruListesi.get(position).b);
        holder.c.setText(soruListesi.get(position).c);
        holder.d.setText(soruListesi.get(position).d);
        holder.cevap.setText(soruListesi.get(position).cevap);
        holder.zorlukSeviyesi.setText(soruListesi.get(position).zorlukSeviyesi);



        holder.ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dosya +=  soruListesi.get(position).soru +" "+ soruListesi.get(position).a +" "+ soruListesi.get(position).b+" "+ soruListesi.get(position).c +" "+ soruListesi.get(position).d+" ";
                Toast.makeText(holder.itemView.getContext(),secilenSoru+"."+" "+"soru seçildi.",Toast.LENGTH_SHORT).show();
            }
        });

        }




    @Override
    public int getItemCount() {
        return soruListesi.size();
    }





    public String getDosya() {
        return dosya;
    }















}
