package com.example.joaquin.triviagranja.jordy;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.joaquin.triviagranja.R;

public class menu extends AppCompatActivity {
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        contador=0;
    }

    public void Actbtn_menu_champey(View v){
        MiAnimacion(v);
        Conteo("Champey",v);
    }

    public void Actbtn_menu_cenotes_candelaria(View v){
        MiAnimacion(v);
        Conteo("Cenores de candelaria",v);
    }

    public void Actbtn_menu_hun_nal(View v){
        MiAnimacion(v);
        Conteo("nombre raro",v);
    }

    public void Actbtn_menu_tikal(View v){
        MiAnimacion(v);
        Conteo("Tikal",v);
    }

    public void Actbtn_menu_laguna_lachua(View v){
        MiAnimacion(v);
        Conteo("Laguna lachua",v);
    }

    public void Actbtn_menu_laguna_brava(View v){
        MiAnimacion(v);
        Conteo("Laguna brava",v);
    }

    public void Actbtn_menu_mirador(View v){
        MiAnimacion(v);
        Conteo("El mirador",v);
    }

    public void Actbtn_menu_laguna_mandalena(View v){
        MiAnimacion(v);
        Conteo("Laguna mandalena",v);
    }

    public void Actbtn_menu_santo_domingo(View v){
        MiAnimacion(v);
        Conteo("Santo domingo",v);
    }

    public void Actbtn_menu_cartagena(View v){
        MiAnimacion(v);
        Conteo("Cartagena",v);
    }

    public void Actbtn_return(View v){
        this.finish();
    }

    private void MiAnimacion( View v){
        v.setEnabled(false);
         Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
         Animation animScale = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
         Animation rotar = AnimationUtils.loadAnimation(this,R.anim.anim_rotate);
         AnimationSet sets = new AnimationSet(false);
            sets.addAnimation(animAlpha);
            sets.addAnimation(animScale);
            sets.addAnimation(rotar);
            v.startAnimation(sets);
        v.setVisibility(View.INVISIBLE);
    }

     private void Conteo(String lugar, View v){
         contador++;
         switch (contador) {
             case 1://Lugar No. 1
                 Toast.makeText(getApplicationContext(), lugar, Toast.LENGTH_LONG).show();
                 break;
             case 2://Lugar No. 2
                 Toast.makeText(getApplicationContext(), lugar, Toast.LENGTH_LONG).show();
                 break;
             case 3://Lugar No. 3 (cerrar todo)
                 Toast.makeText(getApplicationContext(), lugar, Toast.LENGTH_LONG).show();
                 BloqBotones();
                 Runnable clickButton = new Runnable() {
                     @Override
                     public void run() {
                         finish();
                     }
                 };
                 v.postDelayed(clickButton, 2000); //Delay for 3 seconds to show the result

                 break;
         }

     }

    private void BloqBotones(){
        Button bloquear = (Button)findViewById(R.id.btn_menu_champey);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_cartagena);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_cenotes_candelaria);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_hun_nal);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_laguna_brava);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_laguna_lachua);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_tikal);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_laguna_mandalena);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_mirador);
        bloquear.setEnabled(false);
        bloquear = (Button)findViewById(R.id.btn_menu_santo_domingo);
        bloquear.setEnabled(false);
    }

}
