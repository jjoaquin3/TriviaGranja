package com.example.joaquin.triviagranja.jordy;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.R;

public class resultados extends FragmentActivity {

    static public Integer puntaje;
    static public Typeface TF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TF = Typeface.createFromAsset(getAssets(),"font/puntosynumeros.ttf");

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            puntaje=0;
        } else {
            puntaje= extras.getInt("totalp");
        }
        setContentView(R.layout.activity_resultados);
        ViewPager viewPager = (ViewPager) findViewById(R.id.Mipager);
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
    }

    public void return_inicio(View v){
        finish();
    }

    public static class resumen_ganador extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_resumen_ganador, container, false);
            DarPremio(rootView,resultados.puntaje,resultados.TF);
            return rootView;
        }


        private void DarPremio(View padre, int conteo, Typeface fuente){
            TextView txt = (TextView)padre.findViewById(R.id.text_ganador_desc);
            txt.setTypeface(fuente);

            ImageView premio = (ImageView)padre.findViewById(R.id.vista_premio);
            if(conteo>=0 && conteo<=300){
                txt.setTextSize(80);
                txt.setText("Jugo De la Granja");
                premio.setImageResource(R.drawable.premio0);
            }else if (conteo>=301 && conteo<=600){
                txt.setTextSize(80);
                txt.setText("Bolsa Ecologica");
                premio.setImageResource(R.drawable.premio0);
            }else if (conteo>=601 && conteo<=900){
                txt.setTextSize(80);
                txt.setText("Audifonos");
                premio.setImageResource(R.drawable.premio0);
            }else if (conteo>=901 && conteo<=1500){
                txt.setTextSize(80);
                txt.setText("Premio Mayor");
                premio.setImageResource(R.drawable.premio0);
            }else{
                txt.setTextSize(80);
                txt.setText("Jugo De la Granja");
                premio.setImageResource(R.drawable.premio0);
            }
        }

    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

    public static class resumen_punteo extends Fragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_resumen_punteo, container, false);
            TextView pts= (TextView)rootView.findViewById(R.id.text_punteo);

            pts.setTypeface(resultados.TF);
            pts.setTextColor(Color.rgb(77,41,3));

            pts.setText(resultados.puntaje.toString()+"pts.");
            return rootView;
        }
    }



    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return new resumen_punteo();
                case 1:
                    // Games fragment activity
                    return new resumen_ganador();
            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 2;
        }

    }

}