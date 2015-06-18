package com.prismmobile.cyberdustcc;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements EditCallback{


    ArrayList<String> noteList = new ArrayList<>();
    EditText textInput;
    TextAdapter adapter;
    ListView listView;
    FragmentManager fragmentManager;
    Button submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        submitButton = (Button) findViewById(R.id.submit);
        textInput = (EditText) findViewById(R.id.textInput);
        listView = (ListView) findViewById(R.id.listView);

        FileInputStream fileInputStream;
        try {
            fileInputStream = openFileInput("list");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            noteList = (ArrayList<String>) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        adapter = new TextAdapter(this, noteList);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check to make sure field is not blank
                if(!textInput.getText().toString().equals("")) {
                    noteList.add(textInput.getText().toString());
                    textInput.setText("");
                }

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
                                       Bundle bundle = new Bundle();
                                       bundle.putInt("position", position);
                                       bundle.putString("text", noteList.get(position));
                                       EditFragment editFragment = new EditFragment();
                                       editFragment.setArguments(bundle);
                                       fragmentManager.beginTransaction()
                                               .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom,
                                                                    R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                                               .add(R.id.fragment_container, editFragment)
                                               .addToBackStack(EditFragment.class.getSimpleName())
                                               .commit();
                                       submitButton.setVisibility(View.INVISIBLE);


                                       break;
                                   // DELETE
                                   case 1:
                                       noteList.remove(position);
                                       adapter.notifyDataSetChanged();
                                       break;
                                   // SHARE
                                   case 2:
                                       //Start share Intent
                                       Intent sendIntent = new Intent();
                                       sendIntent.setAction(Intent.ACTION_SEND);
                                       sendIntent.putExtra(Intent.EXTRA_TEXT, noteList.get(position));
                                       sendIntent.setType("text/plain");
                                       startActivity(sendIntent);
                                       break;

                               }
                           }
                       })
                       .create();
                builder.show();
                return true;
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "WRITING!");
        try {
            FileOutputStream fileOutputStream = this.openFileOutput("list", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(noteList);
            oos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void OnSubmit(String text, int position) {
        fragmentManager.popBackStack();
        noteList.set(position, text);
        adapter.notifyDataSetChanged();
        submitButton.setVisibility(View.VISIBLE);
    }

    // This is for Lollipop - There's a weird bug where buttons get placed on top of fragments
    // that are on top of other views. I had to override the back button in order to accomodate
    // this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(count == 0) {
            submitButton.setVisibility(View.VISIBLE);
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }





}
