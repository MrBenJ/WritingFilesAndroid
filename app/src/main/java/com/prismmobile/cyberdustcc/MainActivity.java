package com.prismmobile.cyberdustcc;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    List<String> noteList = new ArrayList<>();
    EditText textInput;
    TextAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        Button submitButton = (Button) findViewById(R.id.submit);
        textInput = (EditText) findViewById(R.id.textInput);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(this, noteList);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noteList.add(textInput.getText().toString());
                textInput.setText("");
            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData clip = ClipData.newPlainText(getString(R.string.simple_text), noteList.get(position));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, getString(R.string.text_copied), Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pick an option")
                       .setItems(R.array.options, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               switch(which) {

                                   // EDIT
                                   case 0:
                                       //Start editText Activity


                                       break;
                                   // DELETE
                                   case 1:
                                       noteList.remove(position);
                                       adapter.notifyDataSetChanged();
                                       break;
                                   // SHARE
                                   case 2:
                                       //Start share Intent
                                       break;

                               }
                           }
                       })
                       .create();
                builder.show();
                return false;
            }
        });

    }



}
