package com.example.joaquin.triviagranja.jordy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.joaquin.triviagranja.R;

public class bonus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
    }

    /**
     *
     * Falta programar la logica
     *
     *
     */

    void salir(View v){
        /*Reanudar hilo del reloj de la trivia*/
        this.finish();
    }

}
