package com.github.mjhassanpur.opennote;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteListAdapter extends ArrayAdapter<NoteItem> {

    public NoteListAdapter(Context context, List<NoteItem> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteItem note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.note_title);
        //TextView tvContent = (TextView) convertView.findViewById(R.id.note_content);
        tvTitle.setText(note.getTitle());
        //tvContent.setText(note.getContent());
        return convertView;
    }
}
