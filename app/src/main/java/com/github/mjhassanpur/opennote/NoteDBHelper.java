package com.github.mjhassanpur.opennote;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.LinkedList;

import com.github.mjhassanpur.opennote.NoteDBContract.NoteEntry;


public class NoteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "note.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_NOTE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NoteEntry.KEY_CONTENT + TEXT_TYPE +
            ")";

    private static final String SQL_DELETE_NOTE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;

    public NoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTE_ENTRIES);
        this.onCreate(db);
    }

    public void createEntry(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteEntry.KEY_CONTENT, note.getContent());

        db.insert(NoteEntry.TABLE_NAME,
                null,
                values);

        db.close();
    }

    public List<Note> getAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + NoteEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        List<Note> notes = new LinkedList<Note>();

        Note note;
        if (cursor.moveToFirst()) {
            do {
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setContent(cursor.getString(1));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        return notes;
    }

}
