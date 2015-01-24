package com.github.mjhassanpur.opennote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class EditNoteActivity extends ActionBarActivity implements
        EditNoteFragment.OnFragmentInteractionListener,
        OptionsBarFragment.OnFragmentInteractionListener {

    private static final String NOTE_ID = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private int id;
    private String title;
    private String content;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");
        content = getIntent().getExtras().getString("content");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_48dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_save) {
            FragmentManager fm = getFragmentManager();
            Fragment cFragment = fm.findFragmentById(R.id.edit_note_fragment);
            ((EditNoteFragment) cFragment).onSavePressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) { }

    public Bundle getArguments() {
        Bundle args = new Bundle();
        args.putInt(NOTE_ID, id);
        args.putString(NOTE_TITLE, title);
        args.putString(NOTE_CONTENT, content);
        return args;
    }
}
