package com.example.joaquin.triviagranja.victor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * Created by victor on 30/08/2016.
 */
public class Rext {

    private String album = null;

    public Rext(String dir)
    {
        if(isExternalStorageWritable())
            getAlbumStorageDir(dir);
        else
            System.out.println("no es leible el directorio");
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if(file.exists()) {
            album = file.getAbsolutePath();
        }
        else{
            if (file.mkdirs()) {
                album = file.getAbsolutePath();
            } else {
                //Log.e(LOG_TAG, "Directory not created");
                System.out.println("Directory not created");
            }
        }
        return file;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public Bitmap readImage(String image) throws IOException {
        //Bitmap bitmap1 = BitmapFactory.decodeFile(this.album + "/image");
        //System.out.println(this.album + "/image");
        File file = new File(this.album, image);
        FileInputStream streamIn = new FileInputStream(file);
        Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
        streamIn.close();
        return bitmap;
    }

    public void export(Context c, String databaseName) {
        try {
            //File sd = Environment.getExternalStorageDirectory();
            File sd = new File(album);
//            File data = Environment.getDataDirectory();
            File data = c.getDatabasePath("trivia");

            if (sd.canWrite()) {
                System.out.println("sd " + sd.getAbsolutePath());
                System.out.println("data " + data.getAbsolutePath());
                //String currentDBPath = "//data//"+contexto.getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = databaseName;
//                File currentDB = new File(data, currentDBPath);
                File currentDB = data;
                File backupDB = new File(sd, backupDBPath);

                System.out.println("contexto " + currentDB.getAbsolutePath());
                System.out.println("bk " + backupDB.getAbsolutePath());

                if (currentDB.exists()) {

                    System.out.println("si existe");
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            System.out.println("error interno exportar");
        }
    }


    public void imp(Context c, String databaseName) {
        try {
            //File sd = Environment.getExternalStorageDirectory();
            File sd = new File(album);
            File data = c.getDatabasePath("trivia");

            if (sd.canWrite()) {
                System.out.println("sd " + sd.getAbsolutePath());
                System.out.println("data " + data.getAbsolutePath());

                String backupDBPath = databaseName;
                File currentDB = data;
                File backupDB = new File(sd, backupDBPath);
                if (backupDB.exists()) {
                    System.out.println("si existe");
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            System.out.println("error interno importar");
        }
    }

    public String pathAudio(String audio) {
        //Bitmap bitmap1 = BitmapFactory.decodeFile(this.album + "/image");
        //System.out.println(this.album + "/image");
        File f = new File(this.album +"/"+ audio);
        if(f.exists())
        {
            return f.getAbsolutePath();
        }
        return "";
    }

    public String ruta()
    {
        return album;
    }
}
