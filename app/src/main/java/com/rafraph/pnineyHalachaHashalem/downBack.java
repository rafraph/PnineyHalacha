package com.rafraph.pnineyHalachaHashalem;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class downBack extends Activity {
    public  static Context context;
    public  static Boolean downOrDelEn;
    public  static Boolean downOrDelEs;
    public  static Boolean downOrDelR;
    public  static Boolean downOrDelF;
    public static  String[][] fBook={{"f_moadim","f_peasach","f_shabbat","f_simchat","f_sucot","f_tefila","f_tfilat_nashim","f_yammim","f_zmanim"},{"13","16","30","10","8","26","24","10","17"}};
    public static  String[][] rBook={{"r_moadim","r_pesach","r_shabbat","r_simchat","r_sucot","r_tfila","r_tfilat_nashim","r_yammim","r_zmanim","r_haamvehaarez","r_misphacha"},{"13","16","30","10","8","26","24","10","17","10","10"}};
    public static  String[][] enBook={{"e_moadim","e_pesach","e_shabbat","e_simchat","e_tefila","e_w_prayer","e_yammim","r_zmanim"},{"13","16","30","10","26","24","10","15"}};
    public static  String[][] esBook={{"s_moadim","s_pesach","s_shabbat","s_simchat","s_tfila","s_tfilat_nashim","s_yammim","s_zmanim","s_brachot"},{"13","16","30","10","26","24","10","17","18"}};

    downBack(Context context,Boolean downOrDelEn,Boolean downOrDelEs,Boolean downOrDelR,Boolean downOrDelF){
        new MyTask().execute("my string parameter");
        this.context=context;
        this.downOrDelEn= downOrDelEn;
        this.downOrDelEs= downOrDelEs;
        this.downOrDelR= downOrDelR;
        this.downOrDelF= downOrDelF;
    }
    public static void downLocal(Context context,String fileName)
    {
        File f1=new File(context.getFilesDir() +"/"+fileName);
        String a ="https://ph.yhb.org.il/wp-content/themes/s/"+fileName;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        String data="";
        try {
            url = new URL(a);

            URLConnection conn = url.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            inputLine = br.readLine();
            while(inputLine!=null)
            {
                data+=inputLine;
                inputLine = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            FileWriter writer = new FileWriter(f1);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void loopDownload(Context context,String [][] arrayBook,String check)
    {
        File fileCheck=new File(context.getFilesDir() +"/"+check);
        System.out.println("well");
        if(!fileCheck.exists()) {
            System.out.println("yalla");
            for (int j = 0; j < arrayBook[0].length; j++) {
                downLocal(context, arrayBook[0][j] + "_tochen.html");
                for (int i = 1; i <= Integer.parseInt(arrayBook[1][j]); i++) {
                    downLocal(context, arrayBook[0][j] + "_" + i + ".html");
                    System.out.println(j+":"+i);
                }
            }

            File f1 = new File(context.getFilesDir() + "/" + check);
            try {

                FileWriter writer = new FileWriter(f1);
                writer.append("yes");
                writer.flush();
                writer.close();
                System.out.println("wow");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void loopDelete(Context context,String [][] arrayBook,String check)
    {
        File file=new File(context.getFilesDir() +"/"+check);
        file.delete();
        if(!file.exists()) {

            for (int j = 0; j < arrayBook[0].length; j++) {
                for (int i = 1; i <= Integer.parseInt(arrayBook[1][j]); i++) {
                    file=new File(context.getFilesDir() +"/"+arrayBook[0][j] + "_" + i + ".html");
                    file.delete();

                }
            }

        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_home);

        // This starts the AsyncTask
        // Doesn't need to be in onCreate()
        new MyTask().execute("my string parameter");
    }

    // Here is the AsyncTask class:
    //
    // AsyncTask<Params, Progress, Result>.
    //    Params – the type (Object/primitive) you pass to the AsyncTask from .execute()
    //    Progress – the type that gets passed to onProgressUpdate()
    //    Result – the type returns from doInBackground()
    // Any of them can be String, Integer, Void, etc.
    public static class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            if(downOrDelEn)
                loopDownload(context,enBook,"en.txt");
            else
            {
                loopDelete(context,enBook,"en.txt");
            }
            if(downOrDelEs)
                loopDownload(context,esBook,"es.txt");
            else
            {
                loopDelete(context,esBook,"es.txt");
            }
            if(downOrDelR)
                loopDownload(context,rBook,"r.txt");
            else
            {
                loopDelete(context,rBook,"r.txt");
            }
            if(downOrDelF)
                loopDownload(context,fBook,"f.txt");
            else
            {
                loopDelete(context,fBook,"f.txt");
            }

            // Do something that takes a long time, for example:

            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
}
