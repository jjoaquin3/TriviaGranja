package com.example.joaquin.triviagranja;
import com.example.joaquin.triviagranja.jordy.menu;
import com.example.joaquin.triviagranja.jose.conteo;
import com.example.joaquin.triviagranja.jose.video;
import com.example.joaquin.triviagranja.victor.Categoria;
import com.example.joaquin.triviagranja.victor.ListaCategoriaActivity;
import com.example.joaquin.triviagranja.victor.TriviaActivity;
import com.example.joaquin.triviagranja.victor.Modelo;
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

        //inicializar categorias
        Modelo modelo = new Modelo(this);
        if(modelo.IsEmpty("categoria"))
        {
            String [] categorias = new String[]{"semuc champey","cenotes de candelaría", "hun nal ye", "tikal", "laguna de lachuá",
                    "laguna brava", "el mirador", "laguna magdalena", "santo domingo", "cartagena"};
            Categoria ct;
            for (int i = 0; i < categorias.length; i++) {
                ct = new Categoria(categorias[i],"","","","",Long.parseLong("-1"));
                modelo.addCategoria(ct);
            }
        }
        modelo.destruir();
    }

    public void main_btnPlay(View v)
    {
        System.out.println("----------> Acción del boton Play - Main");
        Intent pantalla_menu  = new Intent(this, menu.class);
        startActivity(pantalla_menu);
    }

    public void main_btnDemo(View v)
    {
        System.out.println("----------> Acción del boton Demo - Main");
        Intent pantalla_conteo = new Intent(this, conteo.class);
        startActivity(pantalla_conteo);
    }

    public void main_btnVideo(View v)
    {
        System.out.println("----------> Acción del boton Video - Main");
        Intent pantalla_video = new Intent(this, video.class);
        startActivity(pantalla_video);
    }

    //acción btn configuración
    public void btnCfg(View v) {
        Intent confIntent = new Intent(this, ListaCategoriaActivity.class);
        startActivity(confIntent);
    }

}


