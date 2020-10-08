package com.example.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private EditText editTextName;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.et_nama_mhs);
        textViewResult = findViewById(R.id.tv_result);
        dbHelper = new DbHelper(this);
    }

    public void insertData(View view) {
            String name = editTextName.getText().toString();

            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbHelper.KEY_NAME, name);
            long id = sqLiteDatabase.insert(dbHelper.TABLE_STUDENTS, null, values);
            Log.d("DATABASE", "Id data: " + id);

            closeKeyboard();

            Toast.makeText(MainActivity.this, "Data Tersimpan", Toast.LENGTH_LONG).show();
        }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void readData(View view) {
        ArrayList<String> data = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbHelper.TABLE_STUDENTS;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        while (c.moveToNext()) {
            data.add(c.getString(c.getColumnIndex(dbHelper.KEY_NAME)));
        }
        c.close();

        for (int i = 0; i < data.size(); i++) {
            textViewResult.setText(textViewResult.getText().toString() + ", " + data.get(i));
        }
    }
}