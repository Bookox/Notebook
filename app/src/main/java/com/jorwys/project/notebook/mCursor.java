package com.jorwys.project.notebook;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Jorwys on 25-12-2014.
 */
public class mCursor extends CursorAdapter {

    public mCursor(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.mlistview,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1=(TextView)view.findViewById(R.id.textView);
        TextView tv2=(TextView)view.findViewById(R.id.textView2);
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        String  fecha=cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
        tv1.setText(titulo);
        tv2.setText(fecha);

    }
}
