package com.example.joaquin.triviagranja.victor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.joaquin.triviagranja.R;

public class ListaCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutCategorias);
        linearLayout.removeAllViews();
        listar();
    }

    private void listar() {
        Modelo md = new Modelo(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutCategorias);
        final Categoria[] categorias = md.getAllCategorias();
        md.destruir();
        if(categorias != null) {
            Button btn;
            for (int i = 0; i < categorias.length; i++) {
                final int aux = i;
                btn = new Button(this);
                btn.setText(categorias[i].getNombre());
                btn.setTextAppearance(this, R.style.listText);
                btn.setWidth(linearLayout.getWidth());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*AlertDialog tmp = createSimpleDialog(categorias[aux].getRowid());
                        tmp.show();*/
                        Intent qyestionIntent = new Intent(getApplicationContext(), ListaPreguntaActivity.class);
                        qyestionIntent.putExtra("categoria",categorias[aux].getRowid());
                        startActivity(qyestionIntent);
                    }
                });
                linearLayout.addView(btn);
            }
        }
    }
}
