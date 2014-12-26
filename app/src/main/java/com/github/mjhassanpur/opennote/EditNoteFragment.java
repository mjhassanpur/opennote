package com.github.mjhassanpur.opennote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;

import java.util.Date;


public class EditNoteFragment extends Fragment {

    private static final String NOTE_ID = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private int mId;
    private String mTitle;
    private String mContent;

    private OnFragmentInteractionListener mListener;

    private EditText editTitle;
    private EditText editContent;
    private Button button;
    private NoteDBHelper noteDBHelper;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment EditNoteFragment.
     */
    public static EditNoteFragment newInstance() {
        return new EditNoteFragment();
    }

    public EditNoteFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_note, container, false);
        editTitle = (EditText) v.findViewById(R.id.edit_note_title);
        editContent = (EditText) v.findViewById(R.id.edit_note_content);
        button = (Button) v.findViewById(R.id.done_button);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = ((EditNoteActivity)getActivity()).getArguments();
        mId = args.getInt(NOTE_ID);
        mTitle = args.getString(NOTE_TITLE);
        mContent = args.getString(NOTE_CONTENT);

        editTitle.setText(mTitle);
        editTitle.setSelection(editTitle.getText().length());
        if (editContent != null) {
            editContent.setHorizontallyScrolling(false);
            editContent.setLines(5);
            editContent.setText(mContent);
            editContent.setSelection(editContent.getText().length());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                note.setId(mId);
                note.setTitle(editTitle.getText().toString());
                note.setContent(editContent.getText().toString());
                noteDBHelper = new NoteDBHelper(getActivity());
                new UpdateNoteTask(note).execute();
            }
        });

        noteDBHelper = new NoteDBHelper(getActivity());
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

    private class UpdateNoteTask extends AsyncTask<Void, Void, Void> {

        private Note note;

        public UpdateNoteTask(Note note) {
            this.note = note;
        }

        protected void onPreExecute() {
            Log.d("EditNoteActivity", "Updating note...");
        }

        protected Void doInBackground(Void... v) {
            note.setEdited(new Date());
            noteDBHelper.updateEntry(note);
            return null;
        }

        protected void onPostExecute(Void v) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

}
