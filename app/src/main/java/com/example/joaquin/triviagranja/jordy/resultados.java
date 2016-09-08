package com.example.joaquin.triviagranja.jordy;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.R;

public class resultados extends FragmentActivity {

    static public Integer puntaje;
    static public Typeface TF;
    static MediaPlayer mp_resumen_ganador;

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
        mp_resumen_ganador = new MediaPlayer();
        resumen_ganador.contexto=this;
        setContentView(R.layout.activity_resultados);
        ViewPager viewPager = (ViewPager) findViewById(R.id.Mipager);
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
             if (position==1){
                 if(puntaje>=0 && puntaje<=300){
                     reproducirSonidoResultados(R.raw.premio_1_nada);
                 }else if (puntaje>=400 && puntaje<=600){
                     reproducirSonidoResultados(R.raw.premio_3_bolsa);
                 }else if (puntaje>=700 && puntaje<=800){
                     //Audio de la gabacha
                 }else if (puntaje>=900 && puntaje<=1100) {
                     reproducirSonidoResultados(R.raw.premio_2_audifonos);
                 }else if (puntaje>=1200 && puntaje<=1500){
                     reproducirSonidoResultados(R.raw.premio_4_selfie);
                 } else {
                     reproducirSonidoResultados(R.raw.premio_1_nada);
                 }
             }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        viewPager.setAdapter(mAdapter);
    }

    private void reproducirSonidoResultados(int id_raw)
    {
        if(mp_resumen_ganador.isPlaying())
            mp_resumen_ganador.stop();
        mp_resumen_ganador.reset();
        mp_resumen_ganador = MediaPlayer.create(this, id_raw);
        mp_resumen_ganador.start();
    }

    public void return_inicio(View v){
        try
        {
            if(mp_resumen_ganador.isPlaying())
            {
                mp_resumen_ganador.stop();
                mp_resumen_ganador.reset();
            }
        } catch (Exception e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
        this.finish();
    }

    @Override
    public void onBackPressed() {

    }

    public static class resumen_ganador extends Fragment {

        public static Context contexto;

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
                txt.setText("Gracias por participar");
                premio.setImageResource(R.drawable.main_logo);
            }else if (conteo>=400 && conteo<=600){
                txt.setTextSize(80);
                txt.setText("Bolsa Ecologica");
                premio.setImageResource(R.drawable.ganador_premio2);
            }else if (conteo>=700 && conteo<=800){
                txt.setTextSize(80);
                txt.setText("Gabacha");
                premio.setImageResource(R.drawable.ganador_premio3);
            }else if (conteo>=900 && conteo<=1100) {
                txt.setTextSize(80);
                txt.setText("Audiculares");
                premio.setImageResource(R.drawable.ganador_premio4);
            }else if (conteo>=1200 && conteo<=1500){
                txt.setTextSize(80);
                txt.setText("selfie stick");
                premio.setImageResource(R.drawable.main_logo);
            }else{
                txt.setTextSize(80);
                txt.setText("Gracias por participar");
                premio.setImageResource(R.drawable.main_logo);
            }
        }
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
                    //MainActivity.mp_fondo.pause();
                    return new resumen_punteo();
                case 1:
                    // Games fragment activity
                    if(mp_resumen_ganador.isPlaying())
                    {
                        mp_resumen_ganador.stop();
                        mp_resumen_ganador.reset();
                    }
                    /*  if(!MainActivity.mp_fondo.isPlaying())
                        MainActivity.mp_fondo.start();*/
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}