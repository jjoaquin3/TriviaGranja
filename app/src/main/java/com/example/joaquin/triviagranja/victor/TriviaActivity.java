package com.example.joaquin.triviagranja.victor;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.jordy.resultados;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaquin.triviagranja.jordy.mi_bonus;

import com.example.joaquin.triviagranja.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class TriviaActivity extends AppCompatActivity {

    MediaPlayer media;
    MediaPlayer mediaPrg = null;
    boolean flag=true;
    Categoria categorias[];
    Pregunta preguntas[];
    Respuesta respuestas[] = new Respuesta[4];
    Pregunta pregunta = null;
    Modelo modelo = new Modelo(this);
    boolean touch_active = true;
    int numPregunta;
    int numCategorias;
    public static int puntos = 0;
    int demo = 0;
    int ContenoBonus=0;

    int PreguntasBonus[];

    CountDownTimer eltime = null;
    long limitetiempo = 150 * 1000;

    public static int multiplicador;
    int numBuenas = 0;
    boolean bonus = false;
    boolean finalizarPorTiempo = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        //MainActivity.mp_fondo.setVolume(0.8f,0.8f);
        //MainActivity.mp_fondo.stop();
        multiplicador = 0;
        puntos = 0;

        Intent intent = getIntent();
        //verificar si es un demo
        demo = intent.getIntExtra("demo",1);
        if(demo == 1)
        {
            numCategorias = 1;
            numPregunta = numCategorias * 3; //limite
            categorias = new Categoria[numCategorias];
            preguntas = new Pregunta[numPregunta];
            Random rm = new Random();
            for (int x = 0; x < categorias.length; x++) {
                int c = rm.nextInt(10 - 1) + 1;
                categorias[x] = modelo.getCategoria(Long.valueOf(String.valueOf(c)));
            }
        }
        else {

            numCategorias = 3;
            numPregunta = numCategorias * 3;//limite
            categorias = new Categoria[numCategorias];
            preguntas = new Pregunta[numPregunta];

            //recuperar categorias
            for (int x = 0; x < categorias.length; x++) {
                categorias[x] = modelo.getCategoria(intent.getStringExtra("categoria" + (x + 1)));
            }

    /*        for (int x = 0; x < categorias.length ; x++) {
                categorias[x] = modelo.getCategoria("categoria"+(x+1));
            }*/
            calcularT();

        }

        recuperar_info();
        colocarListeners(R.id.TVrespuesta1, R.id.IVrespuesta1, 0);
        colocarListeners(R.id.TVrespuesta2, R.id.IVrespuesta2, 1);
        colocarListeners(R.id.TVrespuesta3, R.id.IVrespuesta3, 2);
        colocarListeners(R.id.TVrespuesta4, R.id.IVrespuesta4, 3);
        colocarFonts();

        PreguntasBonus=SelPregBonus();
        //iniciar();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
/*
        System.out.println("regresamos");
        TextView tvpts = (TextView)findViewById(R.id.TVpts);
        tvpts.setText(String.valueOf(puntos) + " pts");
        if (demo == 0) {
            eltime = new CountDownTimer(limitetiempo, 1000) {
                TextView tvtime = (TextView) findViewById(R.id.TVtimer);

                public void onTick(long millisUntilFinished) {
                    limitetiempo = millisUntilFinished;
                    String texto = "";
                    long mili = millisUntilFinished / 1000;
                    int minutos = (int) mili / 60;
                    int segundos = (int) mili - (minutos * 60);
                    if (minutos > 0) {
                        texto += String.valueOf(minutos) + " : ";
                        if (segundos < 10)
                            texto += "0";
                    } else {
                        if (segundos < 10)
                            tvtime.setTextColor(Color.RED);
                    }
                    texto += String.valueOf(segundos);

                    tvtime.setText(texto);
                }

                public void onFinish() {
                    touch_active = false;
                    finalizarPorTiempo = true;
                    tvtime.setText("Fin!");
                    finAct();
                }
            }.start();
        }
        iniciar();
*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        modelo.destruir();
    }

    private void calcularT()
    {
        //calcular el tiempo
        String kktiempo = "0";
        kktiempo = modelo.getTmp();
        limitetiempo = Integer.valueOf(kktiempo) * 1000;
    }

    private void finAct(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent resumen  = new Intent(getApplicationContext(), resultados.class);
                resumen.putExtra("totalp", puntos);
                startActivity(resumen);
                TriviaActivity.this.finish();
            }
        }, 1500);
    }

    private void recuperar_info()
    {
        for (int i = 0; i < categorias.length ; i++) {
            // categorias[i] = modelo.getCategoria(id_cat[i]);
            if (categorias[i] != null) {
                Pregunta preguntas_tmp[] = modelo.getAllPreguntasFiltro(categorias[i]);
                System.out.println("tamaño de " + categorias[i].getNombre() + " " + preguntas_tmp.length);
                if(preguntas_tmp != null) {
                    int limite = 3;//limite
                    if (preguntas_tmp.length >= limite) {
                        ArrayList<Long> prgSeleccionadas = new ArrayList();
                        Random rm = new Random();
                        Pregunta pgrtmp;
                        for (int k = 0; k < limite; k++) {
                            pgrtmp = preguntas_tmp[rm.nextInt(preguntas_tmp.length)];
                            Long auxa = pgrtmp.getRowid();
                            while (prgSeleccionadas.contains(auxa)) {
                                pgrtmp = preguntas_tmp[rm.nextInt(preguntas_tmp.length)];
                                auxa = pgrtmp.getRowid();
                            }
                            prgSeleccionadas.add(auxa);
                            preguntas[(numCategorias*k)+i] = pgrtmp;
                        }
                    }else{ flag = false; touch_active = false; System.out.println("no hay suficientes preguntas");
                        Toast.makeText(this, "no hay suficientes preguntas válidas para la categoria " + categorias[i].getNombre(),Toast.LENGTH_LONG);
                        if(eltime!= null)
                            eltime.cancel();
                        eltime = null;
                        finAct();
                    }
                }else{ flag = false; touch_active = false; System.out.println("categoria mala");
                    Toast.makeText(this, "no hay suficientes preguntas válidas para la categoria " + categorias[i].getNombre(),Toast.LENGTH_LONG);
                    if(eltime!= null)
                        eltime.cancel();
                    eltime = null;
                    finAct();
                }
            }else { flag = false; touch_active = false; System.out.println("una categoria null");}
        }
    }

    private void recuperarRespuestas(Pregunta prg)
    {
        if(prg != null) {
            Respuesta[] rptTrue = modelo.getAllRespuestasFilter(prg, 1);
            Respuesta[] rptFalse = modelo.getAllRespuestasFilter(prg, 0);
            int limitecorrectas = 1;
            int limiteincorrectas = 3;
            Long tmp;
            //almenos 3 respuestas Falsas y 1 Correcta
            Respuesta rspTmp [] = new Respuesta[limitecorrectas + limiteincorrectas];
            if (rptTrue != null && rptFalse != null) {
                if (rptTrue.length >= limitecorrectas && rptFalse.length >= limiteincorrectas) {
                    ArrayList<Long> rspSeleccionadas = new ArrayList();
                    Random rm = new Random();
                    rspTmp[0] = rptTrue[rm.nextInt(rptTrue.length)];
                    rspSeleccionadas.add(rspTmp[0].getRowid());

                    Respuesta pgrtmp;
                    for (int k = 1; k <= limiteincorrectas; k++) {
                        pgrtmp = rptFalse[rm.nextInt(rptFalse.length)];
                        Long auxa = pgrtmp.getRowid();
                        while (rspSeleccionadas.contains(auxa)) {
                            pgrtmp = rptFalse[rm.nextInt(rptFalse.length)];
                            auxa = pgrtmp.getRowid();
                        }
                        rspSeleccionadas.add(auxa);
                        rspTmp[k] = pgrtmp;
                    }

                    int posTmp = rm.nextInt(limitecorrectas + limiteincorrectas);
                    int aux = 0;
                    while (aux < (limitecorrectas + limiteincorrectas)) {
                        respuestas[posTmp] = rspTmp[aux];
                        posTmp++;
                        if (posTmp >= (limitecorrectas + limiteincorrectas)) posTmp = 0;
                        aux++;
                    }
                } else { flag = false; touch_active = false; System.out.println("No hay suficientes respuestas"); }
            } else { flag = false; touch_active = false; System.out.println("Respuestas es null"); }
        }else { flag = false; touch_active = false; System.out.println("Pregunta es null"); }
    }

    private void iniciar()
    {
        //escribir puntos
        numPregunta--;
        if(numPregunta >= 0) {
            if(flag) {
                pregunta = preguntas[numPregunta];
                reproducirPregunta(pregunta.getAudio());
                TextView tvtitulo = (TextView) findViewById(R.id.TVtextoPregunta);
                tvtitulo.setText(pregunta.getTexto());
                //recuperar respuestas de la pregunta actual
                System.out.println("repo " + pregunta.getTexto());
                recuperarRespuestas(pregunta);
                if(flag) {
                    ImageView iv1 = (ImageView) findViewById(R.id.IVrespuesta1);
                    TextView tv1 = (TextView) findViewById(R.id.TVrespuesta1);

                    ImageView iv2 = (ImageView) findViewById(R.id.IVrespuesta2);
                    TextView tv2 = (TextView) findViewById(R.id.TVrespuesta2);

                    ImageView iv3 = (ImageView) findViewById(R.id.IVrespuesta3);
                    TextView tv3 = (TextView) findViewById(R.id.TVrespuesta3);

                    ImageView iv4 = (ImageView) findViewById(R.id.IVrespuesta4);
                    TextView tv4 = (TextView) findViewById(R.id.TVrespuesta4);

                    Animation aparecer = AnimationUtils.loadAnimation(this, R.anim.aparecer_malas);
                    aparecer.reset();

                    colocarAnimacion(iv1, aparecer);
                    colocarAnimacion(tv1, aparecer);
                    colocarAnimacion(iv2, aparecer);
                    colocarAnimacion(tv2, aparecer);
                    colocarAnimacion(iv3, aparecer);
                    colocarAnimacion(tv3, aparecer);
                    colocarAnimacion(iv4, aparecer);
                    colocarAnimacion(tv4, aparecer);

                    iv1.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    iv3.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);
                    iv4.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                    iv1.setImageResource(R.drawable.checktrivia);
                    iv2.setImageResource(R.drawable.checktrivia);
                    iv3.setImageResource(R.drawable.checktrivia);
                    iv4.setImageResource(R.drawable.checktrivia);

                    tv1.setText(respuestas[0].getTexto());
                    tv2.setText(respuestas[1].getTexto());
                    tv3.setText(respuestas[2].getTexto());
                    tv4.setText(respuestas[3].getTexto());
                    touch_active = true;
                }else{ System.out.println("ha existido un error en recuperar respuesta");  }
            }else{ System.out.println("ha existido un error en recuperar pregunta");  }
        }
        else
        {
            System.out.println("fin");
            if(demo == 1)
            {
                this.finish();
            }
            else {
                //pasar al activity de respuestas
                if(eltime!= null)
                    eltime.cancel();
                eltime = null;
                finAct();
            }
        }
    }

    private void colocarListeners(final int TVid, final int IVid, final int Rnum)
    {
        final TextView tv = (TextView) findViewById(TVid);
        if (tv != null) {
            tv.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            //desabilitar el touch
                            if(touch_active) {
                                touch_active = false;
                                RunAnimation(tv, TVid, IVid, Rnum);
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP:

                        case MotionEvent.ACTION_CANCEL: {
                            //habilitar el touch
                            break;
                        }
                    }
                    return true;
                }
            });
        }
    }

    private void RunAnimation(TextView tvs, int TVid, int IVid, int Rnum)
    {
        //recuperar todos los View Trivia
        final ImageView iv1 = (ImageView)findViewById(R.id.IVrespuesta1);
        final TextView tv1 = (TextView)findViewById(R.id.TVrespuesta1);
        boolean borrar1 = false;

        final ImageView iv2 = (ImageView)findViewById(R.id.IVrespuesta2);
        final TextView tv2 = (TextView)findViewById(R.id.TVrespuesta2);
        boolean borrar2 = false;

        final ImageView iv3 = (ImageView)findViewById(R.id.IVrespuesta3);
        final TextView tv3 = (TextView)findViewById(R.id.TVrespuesta3);
        boolean borrar3 = false;

        final ImageView iv4 = (ImageView)findViewById(R.id.IVrespuesta4);
        final TextView tv4 = (TextView)findViewById(R.id.TVrespuesta4);
        boolean borrar4 = false;

        //animación parpadear elección
        Animation papadeo = AnimationUtils.loadAnimation(this, R.anim.papadear);
        papadeo.reset();
        ImageView ivs;
        ivs = (ImageView)findViewById(IVid);
        if(respuestas[Rnum].isCorrecta() == 1) {
            ivs.setImageResource(R.drawable.correcta);
            //sumar puntos
            puntos = puntos + pregunta.getPuntos();
            TextView tvpts = (TextView)findViewById(R.id.TVpts);
            tvpts.setText(String.valueOf(puntos) + " pts");
            numBuenas++;
            //lanzar el bonus
            if(numBuenas >= 3 && demo == 0)
            {
                bonus = true;
                numBuenas = 0;
                multiplicador++;
                if(eltime!=null)
                    eltime.cancel();
                eltime = null;
            }
            media=MediaPlayer.create(this,R.raw.win);
            media.start();
        }
        else {
            numBuenas = 0;
            ivs.setImageResource(R.drawable.incorrecta);
            media=MediaPlayer.create(this,R.raw.fail);
            media.start();
        }
        tvs.clearAnimation();
        tvs.startAnimation(papadeo);

        //animación taladar derecha
        Animation td = AnimationUtils.loadAnimation(this, R.anim.desaparecer_malas);
        td.reset();

        if(TVid != R.id.TVrespuesta1) {
            if(respuestas[0].isCorrecta() == 1)
            {
                iv1.setImageResource(R.drawable.correcta);
            }
            else
            {
                colocarAnimacion(tv1,td);
                colocarAnimacion(iv1,td);
                borrar1 = true;
            }
        }
        if(TVid != R.id.TVrespuesta2) {
            if(respuestas[1].isCorrecta() == 1)
            {
                iv2.setImageResource(R.drawable.correcta);
            }
            else
            {
                colocarAnimacion(tv2,td);
                colocarAnimacion(iv2,td);
                borrar2 = true;
            }
        }
        if(TVid != R.id.TVrespuesta3) {
            if(respuestas[2].isCorrecta() == 1)
            {
                iv3.setImageResource(R.drawable.correcta);
            }
            else
            {
                colocarAnimacion(tv3,td);
                colocarAnimacion(iv3,td);
                borrar3 = true;
            }
        }
        if(TVid != R.id.TVrespuesta4) {
            if(respuestas[3].isCorrecta() == 1)
            {
                iv4.setImageResource(R.drawable.correcta);
            }
            else
            {
                colocarAnimacion(tv4,td);
                colocarAnimacion(iv4,td);
                borrar4 = true;
            }
        }
        //delay
        final boolean b1 = borrar1;
        final boolean b2 = borrar2;
        final boolean b3 = borrar3;
        final boolean b4 = borrar4;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                borrarTmp(tv1,iv1,b1);
                borrarTmp(tv2,iv2,b2);
                borrarTmp(tv3,iv3,b3);
                borrarTmp(tv4,iv4,b4);
            }
        }, 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(media != null) {
                        media.stop();
                }
                if(mediaPrg != null) {
                        mediaPrg.stop();
                }

                if(!finalizarPorTiempo) {
                    if (bonus) {
                        //pasamos al activity del bonus
                        bonus = false;
                        ContenoBonus++;
                        Intent i = new Intent(getApplicationContext(), mi_bonus.class);
                        i.putExtra("p0",ContenoBonus*100);
                        i.putExtra("p1",PreguntasBonus[ContenoBonus-1]);
                        startActivity(i);
                        overridePendingTransition(R.anim.animacion2, R.anim.animacion1);

                    } else {
                        iniciar();
                    }
                }
            }
        }, 3000);

    }

    private void colocarAnimacion(View v, Animation a)
    {
        v.clearAnimation();
        v.startAnimation(a);
    }

    private void borrarTmp(TextView tv, ImageView iv, boolean flag)
    {
        if(flag)
        {
            tv.setVisibility(View.GONE);
            iv.setVisibility(View.GONE);
        }
    }

   /* @Override
    public void onBackPressed()
    {
        //nada
    }*/

    private void colocarFonts()
    {
        TextView tv;
        Typeface TF;

        TF = Typeface.createFromAsset(getAssets(),"font/titulos.otf");
        tv = (TextView)findViewById(R.id.TVtextoPregunta);
        tv.setTypeface(TF);

        TF = Typeface.createFromAsset(getAssets(),"font/puntosynumeros.ttf");
        tv = (TextView)findViewById(R.id.TVpts);
        tv.setTypeface(TF);
        tv = (TextView)findViewById(R.id.TVtimer);
        tv.setTypeface(TF);

        TF = Typeface.createFromAsset(getAssets(),"font/puntosynumeros.ttf");
        tv = (TextView)findViewById(R.id.TVrespuesta1);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
        tv = (TextView)findViewById(R.id.TVrespuesta2);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
        tv = (TextView)findViewById(R.id.TVrespuesta3);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
        tv = (TextView)findViewById(R.id.TVrespuesta4);
        tv.setTypeface(TF);
        tv.setTextColor(Color.rgb(77,41,3));
    }

    private void reproducirPregunta(String audio)
    {
        Rext r = new Rext("Trivia");
        String path = r.pathAudio(audio);
        if(!path.equals(""))
        {
            try {
                Uri myUri1 = Uri.parse(path);
                File f = new File(myUri1.getPath());
                if(f.exists())
                {
                    mediaPrg = MediaPlayer.create(this, myUri1);
                    mediaPrg.start();
                }
            }catch (Exception e)
            {
                System.out.println("existe un error con el audio de la pregunta");
            }
        }
    }

    @Override
    public void onBackPressed() {
        //this.finish();

}
    private int[] SelPregBonus(){
        int aux;
        int[]a = new int[3];
        aux=numAleator();

        if(aux==5){
            a[0]=aux;
            aux--;
            a[1]=aux;
            aux--;
            a[2]=aux;
        }else if (aux==4){
            a[0]=aux;
            aux--;
            a[1]=aux;
            aux--;
            a[2]=aux;
        }else {
            a[0]=aux;
            aux++;
            a[1]=aux;
            aux++;
            a[2]=aux;
        }
        return a;
    }

    private int numAleator(){
        int x=(int)(Math.random()*5 + 1);
        if(x==0||x>5){
            return 1;
        }else{
            return x;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!this.isFinishing()){
            MainActivity.mp_fondo.pause();
        }

        if(eltime != null)
            eltime.cancel();
        eltime = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.mp_fondo.start();

        System.out.println("regresamos");
        TextView tvpts = (TextView)findViewById(R.id.TVpts);
        tvpts.setText(String.valueOf(puntos) + " pts");
        if (demo == 0) {
            eltime = new CountDownTimer(limitetiempo, 1000) {
                TextView tvtime = (TextView) findViewById(R.id.TVtimer);

                public void onTick(long millisUntilFinished) {
                    limitetiempo = millisUntilFinished;
                    String texto = "";
                    long mili = millisUntilFinished / 1000;
                    int minutos = (int) mili / 60;
                    int segundos = (int) mili - (minutos * 60);
                    if (minutos > 0) {
                        texto += String.valueOf(minutos) + " : ";
                        if (segundos < 10)
                            texto += "0";
                    } else {
                        if (segundos < 10)
                            tvtime.setTextColor(Color.RED);
                    }
                    texto += String.valueOf(segundos);

                    tvtime.setText(texto);
                }

                public void onFinish() {
                    touch_active = false;
                    finalizarPorTiempo = true;
                    tvtime.setText("Fin!");
                    finAct();
                }
            }.start();
        }
        iniciar();

    }

}
