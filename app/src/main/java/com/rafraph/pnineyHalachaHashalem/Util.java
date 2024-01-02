package com.rafraph.pnineyHalachaHashalem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String PREFS_NAME = "MyPrefsFile";
    static SharedPreferences mPrefs;
    SharedPreferences.Editor shPrefEditor;
    public static final int HEBREW	 = 0;
    public static final int ENGLISH = 1;
    public static final int RUSSIAN = 2;
    public static final int SPANISH = 3;
    public static final int FRENCH = 4;
    public Dialog acronymsDialog, languageDialog, booksDownloadDialog;
    public EditText TextToDecode;
    String acronymsText;
    int myLanguage = -1;
    public enum TextArrayEnum {
        AUTO_SCROLL_MENU,
        AUTO_SCROLL_SET_SPEED_DIALOG_HEADER
    }

    public Util(Context context){
        mPrefs =  context.getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
        myLanguage = mPrefs.getInt("MyLanguage", -1);
    }

    public void showPopupMenuSettings(View v, Context context)
    {
        PopupMenu popupMenu = new PopupMenu(context, v);

        String configHeaders[] = new String[8];
        if(myLanguage == ENGLISH) {
            configHeaders[0] = "Settings";
            configHeaders[1] = "About";
            configHeaders[2] = "Feedback";
            configHeaders[3] = "Explanation of search results";
            configHeaders[4] = "Acronyms";
            configHeaders[5] = "Approbations";
            configHeaders[6] = "Language / שפה";
        }
        else if(myLanguage == RUSSIAN) {
            configHeaders[0] = "Настройки";
            configHeaders[1] = "Около";
            configHeaders[2] = "Обратная связь";
            configHeaders[3] = "Объяснение результатов поиска";
            configHeaders[4] = "Абревиатуры";
            configHeaders[5] = "Апробации";
            configHeaders[6] = "ЯЗЫК / שפה";
        }
        else if(myLanguage == SPANISH) {
            configHeaders[0] = "Ajustes";
            configHeaders[1] = "Acerca de";
            configHeaders[2] = "Comentarios";
            configHeaders[3] = "Explicacion del resultado de la busqueda";
            configHeaders[4] = "Acronimos";
            configHeaders[5] = "Aprovaciones";
            configHeaders[6] = "Idioma / שפה";
        }
        else if(myLanguage == FRENCH) {
            configHeaders[0] = "Definitions";
            configHeaders[1] = "A Propos de…";
            configHeaders[2] = "Commentaires";
            configHeaders[3] = "Explication de la recherche";
            configHeaders[4] = "Acronymes";
            configHeaders[5] = "Approbations";
            configHeaders[6] = "Langue / שפה";
        }
        else {/*this is the default*/
            configHeaders[0] = "הגדרות";
            configHeaders[1] = "אודות";
            configHeaders[2] = "משוב";
            configHeaders[3] = "הסבר על החיפוש";
            configHeaders[4] = "ראשי תיבות";
            configHeaders[5] = "הסכמות";
            //booksDownload configHeaders[6] = "ספרים להורדה";
            configHeaders[6/*booksDownload 7*/] = "Language / שפה";
        }

        popupMenu.getMenu().add(0,0,0,configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
        popupMenu.getMenu().add(0,1,1,configHeaders[1]);
        popupMenu.getMenu().add(0,2,2,configHeaders[2]);
        popupMenu.getMenu().add(0,3,3,configHeaders[3]);
        popupMenu.getMenu().add(0,4,4,configHeaders[4]);
        popupMenu.getMenu().add(0,5,5,configHeaders[5]);
        popupMenu.getMenu().add(0,6,6,configHeaders[6]);
        //booksDownload popupMenu.getMenu().add(0,7,7,configHeaders[7]);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case 0:/*settings*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Settings");
                            Intent ourIntent = new Intent(context, ourClass);
                            context.startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }

                        break;
                    case 1:/*about*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
                            Intent ourIntent = new Intent(context, ourClass);
                            context.startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }

                        break;
                    case 2:/*Feedback*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
                            Intent ourIntent = new Intent(context, ourClass);
                            context.startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 3:/*Explanation for Search*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
                            Intent ourIntent = new Intent(context, ourClass);
                            context.startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 4:/*acronyms*/
                        acronymsDecode(context);
                        break;
                    case 5:/*hascamot*/
                        hascamotDialog(context);
                        break;
                    case 6:/*language*/
                        languageDialog(context);
                        break;
//                    case 7:/*booksDownload*/
//                        booksDownloadDialog(context);
//                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    void acronymsDecode(Context context)
    {
        // custom dialog
        acronymsDialog = new Dialog(context);
        acronymsDialog.setContentView(R.layout.acronyms);
        acronymsDialog.setTitle("פענוח ראשי תיבות");

        Button dialogButtonExit = (Button) acronymsDialog.findViewById(R.id.dialogButtonExit);
        Button dialogButtonDecode = (Button) acronymsDialog.findViewById(R.id.dialogButtonDecode);
        final TextView decodedText = (TextView) acronymsDialog.findViewById(R.id.textViewDecodedText);
        //final byte[] buffer;
        //final int size;

        TextToDecode = (EditText) acronymsDialog.findViewById(R.id.editTextAcronyms );

        // if button is clicked
        dialogButtonExit.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                acronymsDialog.dismiss();
            }
        });

        dialogButtonDecode.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                acronymsText = "\r\n" + /*"י\"א" */TextToDecode.getText().toString() + " - ";
                acronymsText = acronymsText.replace("\"", "");
                acronymsText = acronymsText.replace("'", "");
                InputStream is;
                String r="לא נמצאו תוצאות";
                int index=0, index_end=0, first=1;
                try
                {
                    is = context.getAssets().open("acronyms.txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String strText  = new String(buffer);

                    while (strText.indexOf(acronymsText, index_end) != -1)
                    {
                        index = strText.indexOf(acronymsText, index);
                        index = strText.indexOf("-", index+1) + 2;
                        index_end = strText.indexOf("\r\n", index);
                        if(first==1)
                        {
                            r = strText.substring (index, index_end);
                            first=0;
                        }
                        else
                            r += ", " + strText.substring (index, index_end);
                    }
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                decodedText.setText(TextToDecode.getText().toString() + " - " + r);

            }
        });
        acronymsDialog.show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    void hascamotDialog(Context context)
    {
        final Dialog dialog = new Dialog(context);
        int fontSize;
        WebView webviewHascmot;
        WebSettings webSettingsHascamot;

        dialog.setContentView(R.layout.note);

        dialog.setTitle(" הסכמות ");

        webviewHascmot = (WebView) dialog.findViewById(R.id.webViewNote1);
        webSettingsHascamot = webviewHascmot.getSettings();
        webSettingsHascamot.setJavaScriptEnabled(true);
        webSettingsHascamot.setDefaultTextEncodingName("utf-8");
        webviewHascmot.requestFocusFromTouch();

        fontSize = mPrefs.getInt("fontSize", 20);
        webSettingsHascamot.setDefaultFontSize(fontSize);
        int backgroundColor = mPrefs.getInt("BlackBackground", 0);
        webviewHascmot.setBackgroundColor(backgroundColor);
        if(backgroundColor == 0)
            webviewHascmot.loadUrl("javascript:document.body.style.color=\"black\";");
        else
            webviewHascmot.loadUrl("javascript:document.body.style.color=\"white\";");
        webviewHascmot.loadUrl("file:///android_asset/hascamot.html");
        dialog.show();
    }

    void languageDialog(Context context)
    {
        languageDialog = new Dialog(context);
        languageDialog.setContentView(R.layout.language);

        Button ButtonSetLanguage = (Button) languageDialog.findViewById(R.id.dialogButtonOK);
        final RadioButton radioHebrew = (RadioButton) languageDialog.findViewById(R.id.radioHebrew);
        final RadioButton radioEnglish = (RadioButton) languageDialog.findViewById(R.id.radioEnglish);
        final RadioButton radioRussian = (RadioButton) languageDialog.findViewById(R.id.radioRussian);
        final RadioButton radioSpanish = (RadioButton) languageDialog.findViewById(R.id.radioSpanish);
        final RadioButton radioFrench = (RadioButton) languageDialog.findViewById(R.id.radioFrench);

        if(myLanguage == -1)
        {
            myLanguage = HEBREW; /*default value*/
            mPrefs =  context.getSharedPreferences(PREFS_NAME, 0);
            shPrefEditor = mPrefs.edit();
            shPrefEditor.putInt("MyLanguage", myLanguage);
            shPrefEditor.commit();
        }
        else
        {
            if(myLanguage == HEBREW)
                radioHebrew.setChecked(true);
            else if(myLanguage == ENGLISH)
                radioEnglish.setChecked(true);
            else if(myLanguage == RUSSIAN)
                radioRussian.setChecked(true);
            else if(myLanguage == SPANISH)
                radioSpanish.setChecked(true);
            else if(myLanguage == FRENCH)
                radioFrench.setChecked(true);
        }

        // if button is clicked
        ButtonSetLanguage.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                if(radioHebrew.isChecked())
                {
                    myLanguage = HEBREW;
                }
                else if(radioEnglish.isChecked())
                {
                    myLanguage = ENGLISH;
                }
                else if(radioRussian.isChecked())
                {
                    myLanguage = RUSSIAN;
                }
                else if(radioSpanish.isChecked())
                {
                    myLanguage = SPANISH;
                }
                else if(radioFrench.isChecked())
                {
                    myLanguage = FRENCH;
                }

                shPrefEditor.putInt("MyLanguage", myLanguage);
                shPrefEditor.commit();

                languageDialog.dismiss();
            }
        });

        languageDialog.show();
    }

    public List<String> getTextArray(TextArrayEnum textArrayEnum){
        List<String> textList = new ArrayList<>();
        switch (textArrayEnum){
            case AUTO_SCROLL_MENU:
                if(myLanguage == HEBREW) {
                    textList.add("הפעל");
                    textList.add("עצור");
                    textList.add("קבע מהירות");
                }
                else if(myLanguage == ENGLISH) {
                    textList.add("Play");
                    textList.add("Stop");
                    textList.add("Set speed");
                }
                else if(myLanguage == RUSSIAN){
                    textList.add("играть");
                    textList.add("Стоп");
                    textList.add("Установить скорость");
                }
                else if(myLanguage == SPANISH){
                    textList.add("Desplazamiento automatico");
                    textList.add("Parar");
                    textList.add("Seleccionar velocidad");
                }
                else if(myLanguage == FRENCH){
                    textList.add("Demarrer");
                    textList.add("Stop");
                    textList.add("Selectionner la vitesse");
                }
            case AUTO_SCROLL_SET_SPEED_DIALOG_HEADER:
                if(myLanguage == HEBREW) {
                    textList.add("בחר מהירות גלילה");
                }
                else if(myLanguage == ENGLISH) {
                    textList.add("Choose auto-scrolling speed");
                }
                else if(myLanguage == RUSSIAN){
                    textList.add("Выберите скорость автоматической прокрутки");
                }
                else if(myLanguage == SPANISH){
                    textList.add("Elija la velocidad de desplazamiento automático");
                }
                else if(myLanguage == FRENCH){
                    textList.add("Choisissez la vitesse de défilement automatique");
                }
        }
        return textList;
    }

//    void booksDownloadDialog(Context context)
//    {
//        booksDownloadDialog = new Dialog(context);
//        booksDownloadDialog.setContentView(R.layout.books_download);
//
//        Button ButtonDownloadBooks = (Button) booksDownloadDialog.findViewById(R.id.dialogButtonDownload);
//        final CheckBox CheckBoxEnglish = (CheckBox) booksDownloadDialog.findViewById(R.id.checkBoxEnglish);
//        final CheckBox CheckBoxRussian = (CheckBox) booksDownloadDialog.findViewById(R.id.checkBoxRussian);
//        final CheckBox CheckBoxSpanish = (CheckBox) booksDownloadDialog.findViewById(R.id.checkBoxSpanish);
//        final CheckBox CheckBoxFrench  = (CheckBox) booksDownloadDialog.findViewById(R.id.checkBoxFrench);
//
//        // if button is clicked
//        ButtonDownloadBooks.setOnClickListener(new View.OnClickListener()
//        {
//            @SuppressLint("NewApi")
//            @Override
//            public void onClick(View v)
//            {
//                if(CheckBoxEnglish.isChecked())
//                {
//                    downloadEnglishBooks();
//                }
//                if(CheckBoxRussian.isChecked())
//                {
//
//                }
//                if(CheckBoxSpanish.isChecked())
//                {
//
//                }
//                if(CheckBoxFrench.isChecked())
//                {
//
//                }
//
//                booksDownloadDialog.dismiss();
//            }
//        });
//
//        booksDownloadDialog.show();
//    }

}
