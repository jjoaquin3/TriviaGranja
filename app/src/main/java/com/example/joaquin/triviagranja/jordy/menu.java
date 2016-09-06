package com.example.joaquin.triviagranja.jordy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.example.joaquin.triviagranja.R;
import com.example.joaquin.triviagranja.jose.conteo;

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
        Conteo("semuc champey",v);
    }

    public void Actbtn_menu_cenotes_candelaria(View v){
        MiAnimacion(v);
        Conteo("cenotes de candelaría",v);
    }

    public void Actbtn_menu_hun_nal(View v){
        MiAnimacion(v);
        Conteo("hun nal ye",v);
    }

    public void Actbtn_menu_tikal(View v){
        MiAnimacion(v);
        Conteo("parque nacional tikal",v);
    }

    public void Actbtn_menu_laguna_lachua(View v){
        MiAnimacion(v);
        Conteo("laguna de lachua",v);
    }

    public void Actbtn_menu_laguna_brava(View v){
        MiAnimacion(v);
        Conteo("laguna brava",v);
    }

    public void Actbtn_menu_mirador(View v){
        MiAnimacion(v);
        Conteo("el mirador",v);
    }

    public void Actbtn_menu_laguna_mandalena(View v){
        MiAnimacion(v);
        Conteo("laguna magdalena",v);
    }

    public void Actbtn_menu_santo_domingo(View v){
        MiAnimacion(v);
        Conteo("santo domingo",v);
    }

    public void Actbtn_menu_cartagena(View v){
        MiAnimacion(v);
        Conteo("cartagena",v);
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

    private String NomCat1,NomCat2,NomCat3;
    private void Conteo(String lugar, View v){
        contador++;
        switch (contador) {
            case 1://Lugar No. 1
                NomCat1=lugar;
                break;
            case 2://Lugar No. 2
                NomCat2=lugar;
                break;
            case 3://Lugar No. 3 (cerrar todo)
                NomCat3=lugar;
                BloqBotones();
                 Runnable clickButton = new Runnable() {
                     @Override
                     public void run() {
                         Intent ii=new Intent(menu.this, conteo.class);
                         ii.putExtra("categoria1", NomCat1);
                         ii.putExtra("categoria2", NomCat2);
                         ii.putExtra("categoria3", NomCat3);
                         startActivity(ii);
                         finish();
                     }
                 };
                 v.postDelayed(clickButton, 2000); //Delay for 2 seconds to show the result
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

