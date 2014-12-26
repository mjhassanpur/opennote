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
import android.widget.Toast;


public class CreateNoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText editTitle;
    private EditText editContent;
    private Button button;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNoteFragment newInstance(String param1, String param2) {
        CreateNoteFragment fragment = new CreateNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateNoteFragment() {
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
        View v = inflater.inflate(R.layout.fragment_create_note, container, false);

        editTitle = (EditText) v.findViewById(R.id.add_note_title);

        editContent = (EditText) v.findViewById(R.id.add_note_content);
        if (editContent != null) {
            editContent.setHorizontallyScrolling(false);
            editContent.setLines(5);
        }

        button = (Button) v.findViewById(R.id.done_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                String editTitleText = editTitle.getText().toString();
                if (editTitleText != null && !editTitleText.isEmpty()) {
                    note.setTitle(editTitle.getText().toString());
                }
                String editContentText = editContent.getText().toString();
                if (editContentText != null && !editContentText.isEmpty()) {
                    note.setContent(editContent.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Cannot save an empty note",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    return;
                }
                new AddNoteTask(note).execute();
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
            Log.d("CreateNoteActivity", "Adding note...");
        }

        protected Void doInBackground(Void... v) {
            NoteDBHelper noteDBHelper = new NoteDBHelper(getActivity());
            noteDBHelper.createEntry(note);
            return null;
        }

        protected void onPostExecute(Void v) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

}
