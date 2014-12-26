package com.github.mjhassanpur.opennote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends ActionBarActivity implements
        NoteListFragment.OnFragmentInteractionListener,
        NoteDetailsFragment.OnFragmentInteractionListener {

    private final static String TAG_NOTE_LIST_FRAGMENT = "TAG_NOTE_LIST_FRAGMENT";
    private final static String TAG_NOTE_DETAILS_FRAGMENT = "TAG_NOTE_DETAILS_FRAGMENT";

    private Toolbar toolbar;
    private NoteListFragment noteListFragment;
    private NoteDetailsFragment noteDetailsFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getFragmentManager();
        Fragment cFragment = fm.findFragmentById(R.id.container);
        if (cFragment == null || cFragment.getClass().equals(NoteListFragment.class)) {
            noteListFragment = NoteListFragment.newInstance();
            fm.beginTransaction()
                    .replace(R.id.container, noteListFragment, TAG_NOTE_LIST_FRAGMENT)
                    .commit();
            setDefaultActionBar();
        } else {
            setAlternateActionBar();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            changeToListFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) { }

    public void setDefaultActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(R.string.app_name);
    }

    public void setAlternateActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.note);
    }

    public void changeToDetailsFragment(Note note) {
        noteDetailsFragment = NoteDetailsFragment.newInstance(note.getId(), note.getTitle(), note.getContent(), note.getEdited());
        fm.beginTransaction()
                .replace(R.id.container, noteDetailsFragment, TAG_NOTE_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
        setAlternateActionBar();
    }

    public void changeToListFragment() {
        noteListFragment = NoteListFragment.newInstance();
        fm.beginTransaction()
                .replace(R.id.container, noteListFragment, TAG_NOTE_LIST_FRAGMENT)
                .commit();
        setDefaultActionBar();
    }

    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            fm.popBackStack();
            changeToListFragment();
        }
    }
}
