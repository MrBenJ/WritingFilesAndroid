package com.prismmobile.cyberdustcc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    List<String> noteList = new ArrayList<String>();
    EditText textInput;
    static TextAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Starting app...");

        Button submitButton = (Button) findViewById(R.id.submit);
        textInput = (EditText) findViewById(R.id.textInput);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(this, noteList);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Submit button clicked");
                noteList.add(textInput.getText().toString());
                textInput.setText("");
            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

    }



}
