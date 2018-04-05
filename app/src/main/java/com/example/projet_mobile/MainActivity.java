package com.example.projet_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public EditText mavariableEditText;
    public ListView mavariableListView;

    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();

    //private static String url = "https://api.androidhive.info/contacts/";
    private static String url = "https://newsapi.org/v2/top-headlines?sources=le-monde&apiKey=96308c15b3bf408b9a0f25678514fc10";

    //private NotesDbAdapter dbadapter;
    //boolean myItemShouldBeEnabled = false;

    /**********************************************************Méthode OnCreate *****************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /********* On affiche le layout de base et sa toolbar **********/

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /********** Création de variables pour manipulation input text, listview et bouton d'ajout ***********************/
        mavariableEditText = (EditText) findViewById(R.id.mavariableEditText);
        mavariableListView = (ListView) findViewById(R.id.mavariableListView);
        Button button      = (Button) findViewById(R.id.button_add_to_list);
        registerForContextMenu(mavariableListView);


        /********** clic sur un élément de la liste listener  ***********************/
        mavariableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // On appelle la fonction permettant de supprimer une entrée de l'array depuis un index

                        //myItemShouldBeEnabled = true;
                //dbadapter.deleteNote(id);
                //fillData();
            }
        });


        /********** Quand on appuie sur le bouton d'ajout de tache  ***********************/
        /*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTextToList();
            }
        });*/

        new GetJSON(url,TAG,mavariableListView,this).execute();

    }

    /******************************** Gestion du CONTEXT menu **************************************************************/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Cursor SelectedTaskCursor = (Cursor) mavariableListView.getItemAtPosition(info.position);
        final String SelectedTask = SelectedTaskCursor.getString(SelectedTaskCursor.getColumnIndex("title"));

        switch(item.getItemId()){
            case R.id.mapsFind:
                Uri location = Uri.parse("geo:0,0?q=" +SelectedTask ); //28+rue+de+la+chance
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                break;

            case R.id.googleSearch:

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, SelectedTask);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }
        return true;
    }


    /******************************** Gestion du menu **************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*********On crée la notification de suppression de toutes les tâches **********/
        if (id == R.id.action_remove_all) {
            //removeAllTextList();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

            // Add the buttons
            builder.setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    removeAllTextList();
                }
            });
            builder.setNegativeButton(R.string.alert_nok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }




    /****************************************** Méthode de traitement liste ****************************************************************/

    public void addTextToList(){

        if(!mavariableEditText.getText().toString().isEmpty()){
           /* dbadapter.createNote(mavariableEditText.getText().toString(),"");
            fillData();
            mavariableEditText.setText("");*/


           // LE vrai
           /*
            todoItems.add(0, mavariableEditText.getText().toString()); // 1
            aa.notifyDataSetChanged(); // 2
            mavariableEditText.setText(""); // 3 - remise à vide de l'EditText
        */

        }
    }

    public void removeAllTextList(){
        //dbadapter.deleteAllNotes();
        //fillData();
        mavariableEditText.setText("");
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
       /* Cursor c = dbadapter.fetchAllNotes();
        startManagingCursor(c);

        String[] from = new String[] { NotesDbAdapter.KEY_TITLE };
        int[] to = new int[] { R.id.text1 };

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, c, from, to);
        //  setListAdapter(notes);  NE PAS UTILISER car c'est un AppCompactActivity et non pas un ListActivity
        mavariableListView.setAdapter(notes);*/
    }

}
