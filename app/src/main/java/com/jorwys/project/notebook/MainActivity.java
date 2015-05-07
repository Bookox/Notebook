package com.jorwys.project.notebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private Toolbar mtoolbar;
    private Button mguardar;
    private EditText mnota, mtitulo;
    private ImageButton b1, b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mnota=(EditText)findViewById(R.id.editText);
        mtitulo=(EditText)findViewById(R.id.editText1);
        b1=(ImageButton)findViewById(R.id.imageButton);
 b2=(ImageButton)findViewById(R.id.imageButton2);
        b3=(ImageButton)findViewById(R.id.imageButton3);
        database data = new database(this);    //llamamos al modulo database
       final  SQLiteDatabase db=data.getWritableDatabase(); //la variabl SQLiteDatabas db sera la base de datos con propiedades de escritura

        mguardar=(Button)findViewById(R.id.guardar); // boton guardar
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mnota.setGravity(Gravity.CENTER_HORIZONTAL);
               mtitulo.setGravity(Gravity.CENTER_HORIZONTAL);
           }
       });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnota.setGravity(Gravity.RIGHT);
                mtitulo.setGravity(Gravity.RIGHT);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnota.setGravity(Gravity.LEFT);
                mtitulo.setGravity(Gravity.LEFT);
            }
        });
        mguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          // si las notas estan vacias mostrar el Toast
          if (mnota.getText().toString().length() ==0 || mtitulo.getText().toString().length()==0){
              Toast.makeText(getApplicationContext(),"Debes escribir algo!",Toast.LENGTH_SHORT).show();
            }
            else{
            // si las notas contienen texto creamos un nuevo intent y le ponemos dos extras titulo y notas
             Intent volver = new Intent(MainActivity.this,notas.class);

              db.execSQL("INSERT INTO notas (titulo,texto,fecha) VALUES ('"+mtitulo.getText().toString()+"','"+mnota.getText().toString()+"','"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"') ");
              db.close();
              startActivity(volver);
          }



            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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

        return super.onOptionsItemSelected(item);
    }
}
