package com.example.joaquin.triviagranja.victor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaquin.triviagranja.R;

import java.util.ArrayList;
import java.util.Random;

public class TriviaActivity extends AppCompatActivity {

    boolean flag=true;
    boolean flag2 = true;
    Categoria categorias[] = new Categoria[3];
    Pregunta preguntas[] = new Pregunta[9];
    Respuesta respuestas[] = new Respuesta[4];
    Pregunta pregunta = null;
    Modelo modelo = new Modelo(this);
    boolean touch_active = true;
    int numPregunta = 9;
    int numCategorias = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        System.out.println("vergas todo de nuevo");
        //recuperar
        if(flag2) {
            System.out.println("paso por on create flag");
            flag2 = false;
            recuperar_info();
            //prueba Crear Cuatros respuestas
            Respuesta r;
            r = new Respuesta("Cierto", 1, new Long("-1"), new Long("1"));
            respuestas[0] = r;
            r = new Respuesta("Falso", 0, new Long("-1"), new Long("1"));
            respuestas[1] = r;
            r = new Respuesta("El puente está hecho con árboles del bosque", 0, new Long("-1"), new Long("1"));
            respuestas[2] = r;
            r = new Respuesta("Ninguna es Correcta", 0, new Long("-1"), new Long("1"));
            respuestas[3] = r;

            colocarListeners(R.id.TVrespuesta1, R.id.IVrespuesta1, 0);
            colocarListeners(R.id.TVrespuesta2, R.id.IVrespuesta2, 1);
            colocarListeners(R.id.TVrespuesta3, R.id.IVrespuesta3, 2);
            colocarListeners(R.id.TVrespuesta4, R.id.IVrespuesta4, 3);

            iniciar();
        }else {
            System.out.println("paso por on create else");
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        modelo.destruir();
    }

    private void recuperar_info()
    {
        //recibir parámetros de las tres categorias
        Long [] id_cat = new Long [3];
        id_cat[0] = new Long("1");
        id_cat[1] = new Long("2");
        id_cat[2] = new Long("3");

        for (int i = 0; i < categorias.length ; i++) {
            categorias[i] = modelo.getCategoria(id_cat[i]);
            if (categorias[i] != null) {
                Pregunta preguntas_tmp[] = modelo.getAllPreguntasFiltro(categorias[i]);
                System.out.println("tamaño de " + categorias[i].getNombre() + " " + preguntas_tmp.length);
                if(preguntas_tmp != null) {
                    int limite = 3;
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
                    }else{ flag = false; System.out.println("no hay suficientes preguntas");}
                }else{ flag = false; System.out.println("categoria mala");}
            }else { flag = false; System.out.println("una categoria null");}
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
                } else { flag = false; System.out.println("No hay suficientes respuestas"); }
            } else { flag = false; System.out.println("Respuestas es null"); }
        }else { flag = false; System.out.println("Pregunta es null"); }
    }

    private void iniciar()
    {
        numPregunta--;
        if(numPregunta >= 0) {
            if(flag) {
                pregunta = preguntas[numPregunta];
                TextView tvtitulo = (TextView) findViewById(R.id.TVtextoPregunta);
                tvtitulo.setText(pregunta.getTexto());
                //recuperar respuestas de la pregunta actual
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
                }else{ System.out.println("ha existido un error"); }
            }else{ System.out.println("ha existido un error"); }
        }
        else
        {
            //pasar al activity de respuestas
            System.out.println("fin");
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
        if(respuestas[Rnum].isCorrecta() == 1)
            ivs.setImageResource(R.drawable.correcta);
        else
            ivs.setImageResource(R.drawable.incorrecta);
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
                iniciar();
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


}