package com.example.joaquin.triviagranja.jordy;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.R;
import com.example.joaquin.triviagranja.victor.TriviaActivity;

public class mi_bonus extends AppCompatActivity {

    Integer puntosG;
    boolean AskCorrecta;
    private MediaPlayer lee_pregunta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_bonus);
        TextView tv;
        Typeface TF;

        Bundle extras = getIntent().getExtras();
        TF = Typeface.createFromAsset(getAssets(),"font/puntosynumeros.ttf");
        tv = (TextView)findViewById(R.id.bonus_TVpts);
        tv.setTypeface(TF);
        puntosG=extras.getInt("p0");
        logica(extras.getInt("p1"));
        tv.setText("+"+ puntosG.toString() +" pts");
        tv = (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
        tv = (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
    }



    private void logica(int i){
        TextView tv;

        switch (i) {
            case 1:
                tv= (TextView)findViewById(R.id.mitex_bonus_p);
                tv.setBackgroundResource(R.drawable.bonus_p1);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
                tv.setWidth(100);
                tv.setBackgroundResource(R.drawable.bonus_lit);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
                tv.setBackgroundResource(R.drawable.bonus_g);//correcta
                lee_pregunta =MediaPlayer.create(this,R.raw.bonus2);
                lee_pregunta.start();
                AskCorrecta=false;
                break;
            case 2:
                tv= (TextView)findViewById(R.id.mitex_bonus_p);
                tv.setBackgroundResource(R.drawable.bonus_p2);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
                tv.setBackgroundResource(R.drawable.bonus_ml);//correcta
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
                tv.setBackgroundResource(R.drawable.bonus_lit);
                lee_pregunta =MediaPlayer.create(this,R.raw.bonus5);
                lee_pregunta.start();
                AskCorrecta=true;
                break;
            case 3:
                tv= (TextView)findViewById(R.id.mitex_bonus_p);
                tv.setBackgroundResource(R.drawable.bonus_p3);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
                tv.setText("5 años");
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
                tv.setText("10 años");//correcta
                lee_pregunta =MediaPlayer.create(this,R.raw.bonus1);
                lee_pregunta.start();
                AskCorrecta=false;
                break;
            case 4:
                tv= (TextView)findViewById(R.id.mitex_bonus_p);
                tv.setBackgroundResource(R.drawable.bonus_p4);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
                tv.setText("Cierto");//correcta
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
                tv.setText("Falso");
                lee_pregunta =MediaPlayer.create(this,R.raw.bonus3);
                lee_pregunta.start();
                AskCorrecta=true;
                break;
            case 5:
                tv= (TextView)findViewById(R.id.mitex_bonus_p);
                tv.setBackgroundResource(R.drawable.bonus_p5);
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
                tv.setText("Falso");
                tv= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
                tv.setText("Cierto");//correcta
               lee_pregunta =MediaPlayer.create(this,R.raw.bonus4);
                lee_pregunta.start();
                AskCorrecta=false;
                break;
        }
    }

    public void ask1(View v){//true
        TextView tv3= (TextView)findViewById(R.id.bonus_IVrespuesta2_2);
        tv3.setEnabled(false);
        colocarAnimacion(v);
        ImageView tv = (ImageView)findViewById(R.id.bonus_IVrespuesta1);
        MediaPlayer sonido;
        if(AskCorrecta){
            sonido =MediaPlayer.create(this,R.raw.win);
            sonido.start();
            tv.setImageResource(R.drawable.correcta);
            TriviaActivity.puntos += TriviaActivity.multiplicador*100;
        }else {
            sonido =MediaPlayer.create(this,R.raw.fail);
            sonido.start();
            tv.setImageResource(R.drawable.incorrecta);
        }
        finalizar(v);
    }
    public void ask2(View v){//false
        TextView tv3= (TextView)findViewById(R.id.bonus_IVrespuesta1_1);
        tv3.setEnabled(false);
        colocarAnimacion(v);
        ImageView tv = (ImageView)findViewById(R.id.bonus_IVrespuesta2_1);
        MediaPlayer sonido;
        if(!AskCorrecta){
            sonido =MediaPlayer.create(this,R.raw.win);
            sonido.start();
            tv.setImageResource(R.drawable.correcta);
            TriviaActivity.puntos += TriviaActivity.multiplicador*100;
        }else {
            sonido =MediaPlayer.create(this,R.raw.fail);
            sonido.start();
            tv.setImageResource(R.drawable.incorrecta);
        }
        finalizar(v);
    }

    private void colocarAnimacion(View v)
    {
        Animation animScale = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        v.startAnimation(animScale);
    }
    private void finalizar(View v){
        Runnable clickButton = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        v.postDelayed(clickButton, 2000); //Delay for 2 seconds to show the result
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!this.isFinishing()){
            MainActivity.mp_fondo.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.mp_fondo.start();
    }

}
