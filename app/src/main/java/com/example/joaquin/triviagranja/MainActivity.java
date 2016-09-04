package com.example.joaquin.triviagranja;
import com.example.joaquin.triviagranja.jordy.menu;
import com.example.joaquin.triviagranja.jose.conteo;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void main_pantalla2(View v)
    {
        Intent p2 = new Intent(this, menu.class);
        startActivity(p2);
    }

    public void main_pantalla3(View v)
    {
        Intent p3 = new Intent(this, conteo.class);
        startActivity(p3);
    }

}


