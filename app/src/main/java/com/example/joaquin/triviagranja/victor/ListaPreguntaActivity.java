package com.example.joaquin.triviagranja.victor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaquin.triviagranja.R;

public class ListaPreguntaActivity extends AppCompatActivity {
    Categoria cat_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pregunta);

        Intent myIntent = getIntent();
        Long tmp = myIntent.getLongExtra("categoria",-1);

        Modelo md = new Modelo(this);
        cat_actual =  md.getCategoria(tmp);
        md.destruir();

        colocarDatosCategoria();

        Button btn = (Button) findViewById(R.id.BTapplyCategoria);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyCategoria();
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutPreguntas);
        linearLayout.removeAllViews();
        listar();
    }

    private void colocarDatosCategoria()
    {
        if(cat_actual != null) {
            EditText et;
            //nombre
            TextView tv = (TextView) findViewById(R.id.TVeditCategoria);
            tv.setText("" + cat_actual.getNombre());
            tv.setTextSize(40);

            et = (EditText) findViewById(R.id.ETnombreCategoria);
            et.setText("" + cat_actual.getNombre());
            //imagen Menu
            /*et = (EditText) findViewById(R.id.ETimagenCategoria);
            et.setText("" + cat_actual.getImgp());*/
        }
    }

    private void listar()
    {
        if(cat_actual != null) {
            Modelo md = new Modelo(this);
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutPreguntas);
            final Pregunta[] preguntas = md.getAllPreguntas(cat_actual);
            Button btn;
            for (int i = 0; i < preguntas.length; i++) {
                final int aux = i;
                btn = new Button(this);
                btn.setText(preguntas[i].getTexto());
                btn.setTextAppearance(this, R.style.listText);
                btn.setWidth(linearLayout.getWidth());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
    /*                    Intent answerIntent = new Intent(getApplicationContext(), AnswerActivity.class);
                        answerIntent.putExtra("pregunta",preguntas[aux].getRowid());
                        startActivity(answerIntent);*/
                        AlertDialog tmp = createSimpleDialog(preguntas[aux].getRowid(), linearLayout);
                        tmp.show();
                    }
                });
                linearLayout.addView(btn);
            }
            md.destruir();
        }
    }

    private void ApplyCategoria()
    {
        Modelo md = new Modelo(this);
        try {
            EditText et;
            String n, ip;

            et = (EditText) findViewById(R.id.ETnombreCategoria);
            n = et.getText().toString();
           /* et = (EditText) findViewById(R.id.ETimagenCategoria);
            ip = et.getText().toString();*/

            if(!n.equals("")) {
                Categoria nv = new Categoria(n, "", "", "", "", cat_actual.getRowid());
                md.updateCategoria(nv);
                //Toast.makeText(this, "Las modificaciones han sido correctas", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            else{
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

    public void addQuestion(View v)
    {
        if(cat_actual != null) {
            Intent AnswerIntent = new Intent(this, ListaRespuestaActivity.class);
            AnswerIntent.putExtra("pregunta", -1);
            AnswerIntent.putExtra("categoria", cat_actual.getRowid());
            startActivity(AnswerIntent);
        }
    }

    private AlertDialog createSimpleDialog(final Long rowid, final LinearLayout l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Qué deseas hacer")
                .setMessage("Eliminar la pregunta o modificarla")
                .setPositiveButton("Modificar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent answerIntent = new Intent(getApplicationContext(), ListaRespuestaActivity.class);
                                answerIntent.putExtra("pregunta",rowid);
                                startActivity(answerIntent);
                            }
                        })
                .setNegativeButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Modelo md = new Modelo(getApplicationContext());
                                md.deletePregunta(rowid);
                                md.destruir();
                                l.removeAllViews();
                                listar();
                            }
                        })
                .setCancelable(true);

        return builder.create();
    }

}
