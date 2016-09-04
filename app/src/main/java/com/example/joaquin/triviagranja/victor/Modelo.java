package com.example.joaquin.triviagranja.victor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

/**
 * Created by victor on 2/09/2016.
 */
public class Modelo {

    //ingresar puede devolver null
    //modificar y eliminar pueden devolver 0
    //getAll puede devolver null

    DBHandler manejador;

    public Modelo(Context context){
        manejador = new DBHandler(context,"trivia",null,1);
    }

    public void destruir()
    {
        try {
            manejador.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean IsEmpty(String tableName)
    {
        boolean respuesta = false;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query(tableName,null, null, null, null, null, null, null);
            if(crs.getCount() == 0)
            {
                respuesta = true;
            }
            crs.close();
            //db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Long addCategoria(Categoria ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "nombre" , ct.getNombre());
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "imagen1" , ct.getImgp());
        contentValues.put( "imagen2" , ct.getImgs());
        contentValues.put( "audio" , ct.getAudio());
        return ingresar("categoria",contentValues);
    }

    public int updateCategoria(Categoria ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "nombre" , ct.getNombre());
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "imagen1" , ct.getImgp());
        contentValues.put( "imagen2" , ct.getImgs());
        contentValues.put( "audio" , ct.getAudio());
        return modificar("categoria",contentValues," rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    public int deleteCategoria(Categoria ct)
    {
        System.out.println("eliminando " + ct.getRowid());
        return eliminar("categoria", " rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    public int deleteCategoria(Long ct)
    {
        System.out.println("eliminando " + ct);
        return eliminar("categoria", " rowid = ?", new String[]{String.valueOf(ct)});
    }

    public Categoria getCategoria(Long rowid)
    {
        Categoria respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query(true,"categoria",new String[] { "rowid", "*" }, " rowid = ? ", new String[]{String.valueOf(rowid)}, null, null, null, null);
            if(crs != null)
            {
                crs.moveToFirst();
                respuesta = new Categoria(crs.getString(1),crs.getString(2),crs.getString(5),crs.getString(3),crs.getString(4),crs.getLong(0));
            }
            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    //----------------------------------

    public Categoria getCategoria(String rowid)
    {
        Categoria respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query(true,"categoria",new String[] { "rowid", "*" }, " nombre LIKE ? ", new String[]{"%"+rowid+"%"}, null, null, null, null);
            if(crs != null)
            {
                crs.moveToFirst();
                respuesta = new Categoria(crs.getString(1),crs.getString(2),crs.getString(5),crs.getString(3),crs.getString(4),crs.getLong(0));
            }
            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    //----------------------------------

    public Categoria[] getAllCategorias()
    {
        Categoria[] respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query("categoria",new String[] { "rowid", "*" }, null, null, null, null, null, null);
            String[] a = crs.getColumnNames();

            if(crs != null)
            {
                respuesta = new Categoria[crs.getCount()];
                crs.moveToFirst();
                for (int i = 0; i < crs.getCount(); i++) {
                    respuesta[i] = new Categoria(crs.getString(1),crs.getString(2),crs.getString(5),crs.getString(3),crs.getString(4),crs.getLong(0));
                    crs.moveToNext();
                }
            }

            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    //Pregunta
    public Pregunta[] getAllPreguntas(Categoria ct)
    {
        Pregunta[] respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query("pregunta",new String[] { "rowid", "*" }, " categoria = ? ", new String[] {String.valueOf(ct.getRowid())}, null, null, null, null);

            if(crs != null)
            {
                respuesta = new Pregunta[crs.getCount()];
                crs.moveToFirst();
                for (int i = 0; i < crs.getCount(); i++) {
                    respuesta[i] = new Pregunta(crs.getString(1),crs.getLong(2),crs.getInt(3), crs.getString(4), crs.getLong(0));
                    crs.moveToNext();
                }
            }

            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }


    //.............................................

    //Select rowid, * from pregunta where
    // categoria = 1
    // and (select count(*) from respuesta where pregunta = pregunta.rowid and correcta = 1) >= 1
    // and (select count(*) from respuesta where pregunta = pregunta.rowid and correcta = 0) >= 2
    public Pregunta[] getAllPreguntasFiltro(Categoria ct)
    {
        Pregunta[] respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.rawQuery("SELECT rowid, * " +
                            "FROM pregunta AS p " +
                            "WHERE categoria = ? " +
                            "AND (SELECT COUNT(*) FROM respuesta WHERE pregunta = p.rowid AND escorrecta = 1) >= 1 " +
                            "AND (SELECT COUNT(*) FROM respuesta WHERE pregunta = p.rowid AND escorrecta = 0) >= 3 ;",
                    new String[]{String.valueOf(ct.getRowid())});
            if(crs != null)
            {
                respuesta = new Pregunta[crs.getCount()];
                crs.moveToFirst();
                for (int i = 0; i < crs.getCount(); i++) {
                    respuesta[i] = new Pregunta(crs.getString(1),crs.getLong(2),crs.getInt(3), crs.getString(4), crs.getLong(0));
                    crs.moveToNext();
                }
            }

            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    //.............................................


    public Pregunta getPrgunta(Long rowid)
    {
        Pregunta respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query(true,"pregunta",new String[] { "rowid", "*" }, " rowid = ? ", new String[]{String.valueOf(rowid)}, null, null, null, null);
            if(crs != null)
            {
                crs.moveToFirst();
                respuesta = new Pregunta(crs.getString(1),crs.getLong(2),crs.getInt(3),crs.getString(4),crs.getLong(0));
            }
            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Long addPregutna(Pregunta ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "categoria" , ct.getCategoria());
        contentValues.put( "puntos" , ct.getPuntos());
        contentValues.put( "audio" , ct.getAudio());
        return ingresar("pregunta",contentValues);
    }

    public int updatePregunta(Pregunta ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "categoria" , ct.getCategoria());
        contentValues.put( "puntos" , ct.getPuntos());
        contentValues.put( "audio" , ct.getAudio());
        return modificar("pregunta",contentValues," rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    public int deletePregunta(Categoria ct)
    {
        System.out.println("eliminando pregunta" + ct.getRowid());
        return eliminar("pregunta", " rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    public int deletePregunta(Long ct)
    {
        System.out.println("eliminando pregunta " + ct);
        return eliminar("pregunta", " rowid = ?", new String[]{String.valueOf(ct)});
    }

    //Respuesta
    public Respuesta[] getAllRespuestas(Pregunta ct)
    {
        Respuesta [] respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query("respuesta",new String[] { "rowid", "*" }, " pregunta = ? ", new String[] {String.valueOf(ct.getRowid())}, null, null, null, null);

            if(crs != null)
            {
                respuesta = new Respuesta[crs.getCount()];
                crs.moveToFirst();
                for (int i = 0; i < crs.getCount(); i++) {
                    respuesta[i] = new Respuesta(crs.getString(1),crs.getInt(2),crs.getLong(3),crs.getLong(0));
                    crs.moveToNext();
                }
            }

            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Respuesta[] getAllRespuestasFilter(Pregunta ct, int filter)
    {
        Respuesta [] respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query("respuesta",new String[] { "rowid", "*" }, " pregunta = ? AND escorrecta = ? ", new String[] {String.valueOf(ct.getRowid()), String.valueOf(filter)}, null, null, null, null);

            if(crs != null)
            {
                respuesta = new Respuesta[crs.getCount()];
                crs.moveToFirst();
                for (int i = 0; i < crs.getCount(); i++) {
                    respuesta[i] = new Respuesta(crs.getString(1),crs.getInt(2),crs.getLong(3),crs.getLong(0));
                    crs.moveToNext();
                }
            }

            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public int deleteRespuesta(Pregunta ct)
    {
        System.out.println("eliminando respuesta " + ct.getRowid());
        return eliminar("respuesta", " rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    public int deleteRespuesta(Long ct)
    {
        System.out.println("eliminando respuesta " + ct);
        return eliminar("respuesta", " rowid = ?", new String[]{String.valueOf(ct)});
    }

    public Respuesta getRespuesta(Long rowid)
    {
        Respuesta respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getReadableDatabase();
            Cursor crs = db.query(true,"respuesta",new String[] { "rowid", "*" }, " rowid = ? ", new String[]{String.valueOf(rowid)}, null, null, null, null);
            if(crs != null)
            {
                crs.moveToFirst();
                respuesta = new Respuesta(crs.getString(1),crs.getInt(2),crs.getLong(3),crs.getLong(0));
            }
            crs.close();
            db.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    public Long addRespuesta(Respuesta ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "escorrecta" , ct.isCorrecta());
        contentValues.put( "pregunta" , ct.getPregunta());
        contentValues.put( "audio" , "");
        return ingresar("respuesta",contentValues);
    }

    public int updateRespuesta(Respuesta ct)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "texto" , ct.getTexto());
        contentValues.put( "escorrecta" , ct.isCorrecta());
        contentValues.put( "pregunta" , ct.getPregunta());
        contentValues.put( "audio" , "");
        return modificar("respuesta",contentValues," rowid = ?", new String[]{String.valueOf(ct.getRowid())});
    }

    private Long ingresar(String tableName, ContentValues contentValues)
    {
        Long respuesta = null;
        try {
            SQLiteDatabase db;
            db = manejador.getWritableDatabase();
            respuesta = db.insert( tableName , null, contentValues );
            db.close();
            db = null;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    private int modificar(String tableName, ContentValues contentValues, String where, String[] whereArg)
    {
        int respuesta = 0;
        try {
            SQLiteDatabase db;
            db = manejador.getWritableDatabase();
            respuesta = db.update( tableName, contentValues, where, whereArg);
            db.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    private int eliminar(String tableName, String where, String[] whereArg)
    {
        int respuesta = 0;
        try {
            SQLiteDatabase db;
            db = manejador.getWritableDatabase();
            respuesta = db.delete( tableName, where, whereArg);
            db.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }
}
