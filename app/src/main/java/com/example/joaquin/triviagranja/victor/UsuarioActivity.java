package com.example.joaquin.triviagranja.victor;

import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joaquin.triviagranja.R;

public class UsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
    }

    public void BTingresar(View v)
    {
        EditText et = (EditText)findViewById(R.id.ETingreso);
        String texto = et.getText().toString();

        Modelo modelo = new Modelo(this);
        if(modelo.getPass(texto))
        {
            Intent confIntent = new Intent(this, confActivity.class);
            startActivity(confIntent);
        }
        else
        {
            Toast.makeText(this,"La contraseña no es correcta",Toast.LENGTH_SHORT).show();
        }
    }
}
