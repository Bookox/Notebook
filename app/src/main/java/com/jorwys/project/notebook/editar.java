package com.jorwys.project.notebook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;


public class editar extends ActionBarActivity {
    EditText ed1,ed2;
    private Toolbar mtoolbar;
    Button b1;
    public Long fila;
    private ImageButton b,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        mtoolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras=getIntent().getExtras();
        fila=extras.getLong("fila");
        database data = new database(this);    //llamamos al modulo database
        final  SQLiteDatabase db=data.getWritableDatabase();
        ed1=(EditText)findViewById(R.id.editText1);
        ed2=(EditText)findViewById(R.id.editText);
        b=(ImageButton)findViewById(R.id.imageButton);
        b2=(ImageButton)findViewById(R.id.imageButton2);
        b3=(ImageButton)findViewById(R.id.imageButton3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setGravity(Gravity.CENTER_HORIZONTAL);
                ed2.setGravity(Gravity.CENTER_HORIZONTAL);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setGravity(Gravity.RIGHT);
                ed2.setGravity(Gravity.RIGHT);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed2.setGravity(Gravity.LEFT);
                ed1.setGravity(Gravity.LEFT);
            }
        });
        b1=(Button)findViewById(R.id.guardar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().length() ==0 || ed2.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Debes escribir algo!", Toast.LENGTH_SHORT).show();
                }
                else{
                    // si las notas contienen texto creamos un nuevo intent y le ponemos dos extras titulo y notas
                    Intent volver = new Intent(editar.this,notas.class);

                    ContentValues cv= new ContentValues();
                    cv.put("titulo",ed1.getText().toString());
                    cv.put("texto",ed2.getText().toString());
                    cv.put("fecha",java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
                    db.update("notas", cv, "_id " + "=" + fila, null);
                    db.close();
                    startActivity(volver);
                }
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        // Execute LoadNotes() AsyncTask
        new LoadNotes().execute(fila);
    }

    // LoadNotes() AsyncTask
    private class LoadNotes extends AsyncTask<Long, Object, Cursor> {
        // Calls DatabaseConnector.java class
        database dbConnector = new database(editar.this);

        @Override
        protected Cursor doInBackground(Long... params) {
            // Pass the Row ID into GetOneNote function in
            // DatabaseConnector.java class
            SQLiteDatabase lectura =dbConnector.getWritableDatabase();
            return lectura.query(dbConnector.TABLE, null, dbConnector.id + "=" + fila, null, null,
                    null, null);

        }

        @Override
        protected void onPostExecute(Cursor result) {
                super.onPostExecute(result);

            result.moveToFirst();
            // Retrieve the column index for each data item
            int TitleIndex = result.getColumnIndex("titulo");
            int NoteIndex = result.getColumnIndex("texto");

            // Set the Text in TextView
            ed1.setText(result.getString(TitleIndex));
            ed2.setText(result.getString(NoteIndex));

            result.close();
            dbConnector.close();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
            {
            AlertDialog.Builder dev= new AlertDialog.Builder(this);
            dev.setIcon(R.drawable.notebook)
                    .setTitle("Developer!")
                    .setMessage("Jordy Oquendo, 0412-6602841")
                    .setPositiveButton("Okey :)",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            dev.show();
            return true;
        }
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ed1.getText().toString() + Html.fromHtml("<br />") + ed2.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Comparte tu nota"));
        }

        return super.onOptionsItemSelected(item);
    }
}
