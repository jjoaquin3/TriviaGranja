package com.example.joaquin.triviagranja.victor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joaquin.triviagranja.R;

import java.io.File;
import java.io.IOException;




public class confActivity extends AppCompatActivity {

    Modelo modelo = new Modelo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.minutos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.seg_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        /*EditText et = (EditText) findViewById(R.id.ETvideo);
        et.setText(modelo.getVideo());*/

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        modelo.destruir();
    }

   public void BTch(View v)
    {
        Intent confIntent = new Intent(this, ListaCategoriaActivity.class);
        startActivity(confIntent);
    }

     public void BTp(View v)
    {
        String nvo = "";
        String viejo = "";
        EditText et = (EditText) findViewById(R.id.EToldPass);
        viejo = et.getText().toString();
        System.out.println(viejo);
        if(modelo.getPass(viejo))
        {
            et = (EditText) findViewById(R.id.ETnuevaPass);
            nvo = et.getText().toString();
            System.out.println(nvo);
            modelo.setPass(nvo);
            Toast.makeText(this,"La contraseña se ha actualizado",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this,"La contraseña anterior no es correcta",Toast.LENGTH_SHORT).show();
        }
    }

    public void BTim(View v)
    {
        Rext r = new Rext("Trivia");
        r.imp(this,"trivia.db");
        Toast.makeText(this,"Datos importados",Toast.LENGTH_SHORT).show();

    }

    public void BTex(View v)
    {
        Rext r = new Rext("Trivia");
        r.export(this,"trivia.db");
        Toast.makeText(this,"Datos exportados",Toast.LENGTH_SHORT).show();
    }

   /* public void BTv(View v)
    {
        String video = "";
        EditText et = (EditText) findViewById(R.id.ETvideo);
        video = et.getText().toString();
        modelo.setVideo(video);
        Toast.makeText(this,"Ruta de video se ha actualizado",Toast.LENGTH_SHORT).show();

        //leer ruta video
        Rext r = new Rext("Trivia");
        System.out.println(r.ruta()+"/"+modelo.getVideo());
    }*/

    public void BTt(View v)
    {
        int min = 0, seg = 0;
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        min = Integer.valueOf(sp.getSelectedItem().toString());
        sp = (Spinner) findViewById(R.id.spinner2);
        seg = Integer.valueOf(sp.getSelectedItem().toString());
        String tmp = String.valueOf(min * 60 + seg);
        modelo.setTmp(tmp);

        Toast.makeText(this,"Tiempo de juego se ha actualizado",Toast.LENGTH_SHORT).show();
    }
}
