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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static MediaPlayer mp_fondo;
    private ArrayList<Integer> playlist;
    private int id_raw =0;
    public static float volumen = 0.4f;
    public static float volumenmax = 1.0f;

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
        sonidoStart();
    }

    private void sonidoStart()
    {
        playlist = new ArrayList<>();
        playlist.add(R.raw.kevin_macleod_master_of_the_feast);
        playlist.add(R.raw.michael_curtis_no);
        mp_fondo = new MediaPlayer();
        this.playSong();
    }

    private void playSong()
    {
        if(mp_fondo.isPlaying())
            mp_fondo.stop();
        mp_fondo.reset();
        mp_fondo = MediaPlayer.create(MainActivity.this, playlist.get(id_raw));
        mp_fondo.start();
        mp_fondo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                nextSong();
            }
        });
    }

    private void nextSong()
    {
        if(id_raw ==0)
            id_raw++;
        else
            id_raw =0;
        playSong();
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
        mp_fondo.pause();
        startActivity(pantalla_video);
    }

    //acción btn configuración
    public void btnCfg(View v) {
        Intent confIntent = new Intent(this, UsuarioActivity.class);
        mp_fondo.pause();
        startActivity(confIntent);
    }

    @Override
    public void onBackPressed() {
        try
        {
            mp_fondo.stop();
            mp_fondo.reset();
        } catch (Exception e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!this.isFinishing()){
            //Insert code for HOME  key Event
            mp_fondo.setVolume(volumen,volumen);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mp_fondo.isPlaying())
            mp_fondo.start();
        mp_fondo.setVolume(volumenmax,volumenmax);
    }

    @Override
    protected void  onDestroy()
    {
        super.onDestroy();
        mp_fondo.stop();
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


