package com.ocak.ders;

public class Soru {

     Integer id;
     String soru, a,b,c,d,cevap,zorlukSeviyesi;

     public Soru(Integer id,String soru ,String a ,String b, String c, String d, String cevap, String zorlukSeviyesi){

        this.id = id;
        this.soru = soru;
        this.a = a;
        this.b =b;
        this.c = c;
        this.d = d;
        this.cevap = cevap;
        this.zorlukSeviyesi = zorlukSeviyesi;


    }


}
