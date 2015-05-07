package com.jorwys.project.notebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class notas extends ActionBarActivity {
    private Toolbar mtoolbar; // toolbar
    private ListView mlistview; // listview
    mCursor adaptador; // adaptador
    database db = new database(this); // nuevo acceso a la base de datos
    FloatingActionButton fab; //floating action button
    String[] allColumns = new String[] { db.id,db.titulo,db.fecha}; // variables para hacer el query
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

       final SQLiteDatabase lectura =db.getWritableDatabase(); //llamamos a la database , y al SqliteDatabase


        //creamos una consulta con un cursor y asiganmos el cursor al adaptador
        Cursor cursor = lectura.query(db.TABLE,allColumns,null,null,null,null,null);
        if (cursor!=null){ cursor.moveToFirst();}

        adaptador=new mCursor(this,cursor);

        //asignamos la toolbar con actionBar
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        //asignamos la listview a su vista
        mlistview = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

       //ejecutamos e iniciamos el array adapter y lo amarramos al listview
        mlistview.setAdapter(adaptador);

        //ejecutamos el click listener en el boton fab.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui ejecutamos el unico otro id de nuestro menu que es el boton agregar y ejecutamos el Main activ
                Intent i = new Intent(notas.this, MainActivity.class);
                startActivity(i);

            }
        });
        fab.attachToListView(mlistview);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Open ViewNote activity
                Intent editar = new Intent(notas.this, editar.class);

                // Pass the ROW_ID to ViewNote activity
                editar.putExtra("fila",id);
                startActivity(editar);
            }
        });

        mlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView<?> parent, View view, int position,  final long id) {
                //creamos el builder del dialogo a mostrar si deseamos borrar los mensajes
                AlertDialog.Builder dialog= new AlertDialog.Builder(notas.this);
                dialog.setTitle("Borrar nota")
                        .setMessage("Seguro que deseas borrar esta nota?")
                        .setIcon(R.drawable.remove)
                        .setPositiveButton("Borrar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //creamos un delete de los campos donde el _id sea = al row del listview
                               lectura.delete(database.TABLE,database.id + "=" + id, null);
                               //asignamos un nuevo cursor
                                Cursor nuevo = lectura.query(db.TABLE,allColumns,null,null,null,null,null);
                               // asignamos el nuevo cursor al adaptador del custom cursor
                                adaptador.changeCursor(nuevo);

                            }
                        }).setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                dialog.create();
                dialog.show();

                return true;
            }
        });

    }



    @Override
    protected void onResume(){
        super.onResume();
       adaptador.notifyDataSetChanged();
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
        else {

        }
        return super.onOptionsItemSelected(item);
    }


}
