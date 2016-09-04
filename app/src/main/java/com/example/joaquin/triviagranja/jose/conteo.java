package com.example.joaquin.triviagranja.jose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.joaquin.triviagranja.R;

public class conteo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);
    }

    public void conteo_retroceso(View v)
    {
        this.finish();
    }

}
