package com.example.joaquin.triviagranja;
import com.example.joaquin.triviagranja.jordy.menu;
import com.example.joaquin.triviagranja.jose.conteo;
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

    public void main_pantalla2(View v)
    {
        Intent p2 = new Intent(this, menu.class);
        startActivity(p2);
    }

    public void main_pantalla3(View v)
    {
        Intent p3 = new Intent(this, conteo.class);
        startActivity(p3);
    }

    //acción btn configuración
    public void btnCfg(View v) {
        Intent confIntent = new Intent(this, ListaCategoriaActivity.class);
        startActivity(confIntent);

    }

}


