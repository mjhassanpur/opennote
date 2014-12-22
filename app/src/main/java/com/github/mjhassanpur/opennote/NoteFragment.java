package com.github.mjhassanpur.opennote;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import java.util.List;


public class NoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tv;
    private EditText et;
    private NoteDBHelper noteDBHelper;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        tv = (TextView) v.findViewById(R.id.view_note);

        et = (EditText) v.findViewById(R.id.add_note);
        if (et != null) {
            et.setHorizontallyScrolling(false);
            et.setLines(5);
        }
        et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                switch (result) {
                    case EditorInfo.IME_ACTION_DONE:
                        Note note = new Note();
                        note.setContent(et.getText().toString());
                        new AddNoteTask(note).execute();
                        return true;
                }
                return false;
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class AddNoteTask extends AsyncTask<Void, Void, Void> {

        private Note note;

        public AddNoteTask(Note note) {
            this.note = note;
        }

        protected void onPreExecute() {
            Log.d("MainActivity", "Adding note...");
        }

        protected Void doInBackground(Void... v) {
            noteDBHelper = new NoteDBHelper(getActivity());
            noteDBHelper.createEntry(note);
            return null;
        }

        protected void onPostExecute(Void v) {
            et.getText().clear();
            new GetNotesTask().execute();
        }
    }

    private class GetNotesTask extends AsyncTask<Void, Void, Void> {

        List<Note> notes;

        protected void onPreExecute() {
            Log.d("MainActivity", "Getting Notes...");
        }

        protected Void doInBackground(Void... v) {
            notes = noteDBHelper.getAllEntries();
            return null;
        }

        protected void onPostExecute(Void v) {
            tv.setText(notes.get(0).getId() + ". " + notes.get(0).getContent() + "\n");
            for (int i = 1; i < notes.size(); ++i) {
                tv.append(notes.get(i).getId() + ". " + notes.get(i).getContent() + "\n");
            }
        }
    }

}
