package com.example.joaquin.triviagranja.jose;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.example.joaquin.triviagranja.R;

public class conteo extends AppCompatActivity {

    ImageView image;
    private AlphaAnimation fadeIn, fadeOut;
    private final int conteo_duracion_img = 750;
    private final int conteo_duracion_desvanecer = 250;
    private int conteo = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        image = (ImageView)findViewById(R.id.imageView_conteo_numeros);

        this.efectosStart();
        this.efectosEnd();

        image.startAnimation(fadeIn);
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
                image.setImageBitmap(bmtmp);
                conteo--;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.startAnimation(fadeOut);
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
                    image.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void conteo_btnAtras(View v)
    {
        this.finish();
    }
}
