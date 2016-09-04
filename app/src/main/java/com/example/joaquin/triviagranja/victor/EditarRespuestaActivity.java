package com.example.joaquin.triviagranja.victor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.joaquin.triviagranja.R;

public class EditarRespuestaActivity extends AppCompatActivity {

    Respuesta r_actual = null;
    Long prg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_respuesta);

        Intent myIntent = getIntent();
        final Long tmp = myIntent.getLongExtra("respuesta",-1);
        prg_id = myIntent.getLongExtra("pregunta", -1);

        Modelo md = new Modelo(this);
        r_actual = md.getRespuesta(tmp);
        md.destruir();

        colocarDatosRespuesta();

        Button btn = (Button) findViewById(R.id.BTapplyRespuesta);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyRespuesta(tmp);
            }
        });

    }

    private void colocarDatosRespuesta()
    {
        if(r_actual != null) {
            EditText et;
            //nombre
            et = (EditText) findViewById(R.id.ETtextoRespuesta);
            et.setText("" + r_actual.getTexto());
            //correcta
            Switch sw = (Switch) findViewById(R.id.SWRespuesta);
            if (r_actual.isCorrecta() > 0)
                sw.setChecked(true);
            else
                sw.setChecked(false);
        }
    }

    private void ApplyRespuesta(Long tmp)
    {
        Modelo md = new Modelo(this);
        try {
            EditText et = (EditText) findViewById(R.id.ETtextoRespuesta);
            String t = et.getText().toString();

            Switch sw = (Switch) findViewById(R.id.SWRespuesta);
            int c;
            if (sw.isChecked())
                c = 1;
            else
                c = 0;

            if(!t.equals("")) {
                if (tmp >= 0) {
                    //modificar
                    Respuesta nv = new Respuesta(t, c, r_actual.getPregunta(), r_actual.getRowid());
                    md.updateRespuesta(nv);
                } else {
                    //ingresar
                    Respuesta nv = new Respuesta(t, c, prg_id, Long.parseLong("-1"));
                    md.addRespuesta(nv);
                }
                //Toast.makeText(this, "Las modificaciones han sido correctas", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            else
            {
                Toast.makeText(this, "Debe llenar los campos con *", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(this, "Las modificaciones no han sido correctas", Toast.LENGTH_SHORT).show();
        }
        finally {
            md.destruir();
        }
    }
}
