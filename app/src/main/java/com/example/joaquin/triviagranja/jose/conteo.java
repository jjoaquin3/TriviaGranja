package com.example.joaquin.triviagranja.jose;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.R;
import com.example.joaquin.triviagranja.victor.Categoria;
import com.example.joaquin.triviagranja.victor.TriviaActivity;

public class conteo extends AppCompatActivity {

    ImageView imageView_conteo_numeros;
    private AlphaAnimation fadeIn, fadeOut;
    private final int conteo_duracion_img = 450;//750
    private final int conteo_duracion_desvanecer = 150;//250
    private int conteo = 3;
    Categoria categorias[] = new Categoria[3];
    private MediaPlayer mp_fondo_conteo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        imageView_conteo_numeros = (ImageView)findViewById(R.id.imageView_conteo_numeros);

        this.efectosStart();
        this.efectosEnd();

        imageView_conteo_numeros.startAnimation(fadeIn);

        MainActivity.mp_fondo.pause();
        mp_fondo_conteo =MediaPlayer.create(conteo.this,R.raw.rsg);
        mp_fondo_conteo.start();
    }

    private void efectosStart()
    {
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(conteo_duracion_img);
        fadeIn.setStartOffset(conteo_duracion_desvanecer);
        fadeIn.setFillAfter(true);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Bitmap bmtmp;
                if(conteo==3)
                    bmtmp= BitmapFactory.decodeResource(getResources(), R.drawable.conteo_3);
                else if(conteo==2)
                    bmtmp= BitmapFactory.decodeResource(getResources(), R.drawable.conteo_2);
                else
                    bmtmp= BitmapFactory.decodeResource(getResources(), R.drawable.conteo_1);
                imageView_conteo_numeros.setImageBitmap(bmtmp);
                conteo--;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView_conteo_numeros.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void efectosEnd()
    {
        fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(conteo_duracion_img);
        fadeOut.setStartOffset(conteo_duracion_desvanecer);
        fadeOut.setFillAfter(true);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(conteo > 0 )
                    imageView_conteo_numeros.startAnimation(fadeIn);
                else
                    finConteo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void conteo_btnAtras(View v)
    {
        try
        {
            if(mp_fondo_conteo.isPlaying())
                mp_fondo_conteo.stop();
            MainActivity.mp_fondo.start();
        } catch (Exception e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
        this.finish();
    }

    private void finConteo()
    {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Toast.makeText(getApplicationContext(), ":(", Toast.LENGTH_SHORT).show();
        } else {
            Intent inicio  = new Intent(this, TriviaActivity.class);
            inicio.putExtra("categoria1", extras.getString("categoria1"));
            inicio.putExtra("categoria2", extras.getString("categoria2"));
            inicio.putExtra("categoria3", extras.getString("categoria3"));
            int d = 0;
            inicio.putExtra("demo",d);

            MainActivity.mp_fondo.start();
            startActivity(inicio);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        try
        {
            if(mp_fondo_conteo.isPlaying())
                mp_fondo_conteo.stop();
            MainActivity.mp_fondo.start();
        } catch (Exception e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
        this.finish();
    }
}
