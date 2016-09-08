package com.example.joaquin.triviagranja;

import com.example.joaquin.triviagranja.jordy.menu;
import com.example.joaquin.triviagranja.jose.video2;
import com.example.joaquin.triviagranja.victor.Categoria;
import com.example.joaquin.triviagranja.victor.Modelo;
import com.example.joaquin.triviagranja.victor.TriviaActivity;
import com.example.joaquin.triviagranja.victor.UsuarioActivity;
import com.example.joaquin.triviagranja.victor.Rext;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    public static MediaPlayer fondo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Rext r = new Rext("Trivia");
        //inicializar categorias
        Modelo modelo = new Modelo(this);
        if(modelo.IsEmpty("conf"))
        {
            modelo.inicializarpass();
        }

        if(modelo.IsEmpty("categoria"))
        {
            String [] categorias = new String[]{"semuc champey","cenotes de candelaría", "hun nal ye", "parque nacional tikal", "laguna de lachuá",
                    "laguna brava", "el mirador", "laguna magdalena", "santo domingo", "cartagena"};
            Categoria ct;
            for (int i = 0; i < categorias.length; i++) {
                ct = new Categoria(categorias[i],"","","","",Long.parseLong("-1"));
                modelo.addCategoria(ct);
            }
        }
        modelo.destruir();

        fondo=MediaPlayer.create(MainActivity.this,R.raw.m_fondo);
        fondo.setLooping(true);
        fondo.start();
    }

    public void main_btnPlay(View v)
    {
        System.out.println("----------> Acción del boton Play - Main");
        AlertDialog tmp = createSimpleDialog();
        tmp.show();
    }

    public void main_btnDemo(View v)
    {
        System.out.println("----------> Acción del boton Demo - Main");
        Intent pantalla_conteo = new Intent(this, TriviaActivity.class);
        int d = 1;
        pantalla_conteo.putExtra("demo",d);
        startActivity(pantalla_conteo);
    }

    public void main_btnVideo(View v)
    {
        System.out.println("----------> Acción del boton Video - Main");
        Intent pantalla_video = new Intent(this, video2.class);
        fondo.pause();
        startActivity(pantalla_video);
    }

    //acción btn configuración
    public void btnCfg(View v) {
        Intent confIntent = new Intent(this, UsuarioActivity.class);
        startActivity(confIntent);
    }

    public void onBackPressed() {
        fondo.stop();
        this.finish();
    }

    private AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Elije tu género")
                .setMessage("¿Eres Hombre o Mujer?")
                .setPositiveButton("Hombre",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent pantalla_menu  = new Intent(getApplicationContext(), menu.class);
                                pantalla_menu.putExtra("genero",0);
                                startActivity(pantalla_menu);
                            }
                        })
                .setNegativeButton("Mujer",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent pantalla_menu  = new Intent(getApplicationContext(), menu.class);
                                pantalla_menu.putExtra("genero",1);
                                startActivity(pantalla_menu);
                            }
                        })
                .setCancelable(true);

        return builder.create();
    }

}


