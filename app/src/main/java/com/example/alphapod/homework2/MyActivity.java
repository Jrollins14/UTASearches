package com.example.alphapod.homework2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyActivity extends Activity {



    ArrayList<String> tags = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tags);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
                loadPreferences(tags.get(position));
            }
        });

        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id)
            {
                //do your stuff here
                final CharSequence[] items = {"Share", "Edit", "Delete"};
                final String tag = (tags.get(position));
                String msg = String.format("Share, Edit or Delete the search tagged as \"%s\"", tag);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle(msg);

                alertDialogBuilder

                        .setCancelable(false)
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    // TODO Auto-generated method stub
                                    String selected = items[arg1].toString();

                                    if(selected.equals("Share"))
                                    {

                                    }
                                    else if(selected.equals("Edit"))
                                    {

                                    }
                                    else if(selected.equals("Delete"))
                                    {
                                        tags.remove(tag.toString());
                                        adapter.notifyDataSetChanged();


                                    }

                                }

                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });


                 


                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveClick(View v)
    {
        String tag;
        String query;

        EditText searchBox = (EditText) findViewById(R.id.searchBox);
        EditText tagBox = (EditText) findViewById(R.id.tagBox);

        tag = tagBox.getText().toString();
        query = searchBox.getText().toString();

        tags.add(tagBox.getText().toString());
        editor.putString(tag, query);
        editor.commit();
        searchBox.setText("");
        tagBox.setText("");

        adapter.notifyDataSetChanged();

        //hide keyboard
        ((InputMethodManager) getSystemService (
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                searchBox.getWindowToken(), 0);
    }

    private void loadPreferences(String key)
    {
        String url = "http://www.uta.edu/search/?q=";
        String search;
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String keyWord = sharedPreferences.getString(key, "");

        search = String.format("%s%s", url, keyWord);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(search));

        startActivity(i);
    }



}
