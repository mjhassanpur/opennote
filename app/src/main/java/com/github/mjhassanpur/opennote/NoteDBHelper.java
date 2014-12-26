package com.github.mjhassanpur.opennote;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;

import com.github.mjhassanpur.opennote.NoteDBContract.NoteEntry;


public class NoteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "note.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_NOTE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NoteEntry.KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                    NoteEntry.KEY_CONTENT + TEXT_TYPE + COMMA_SEP +
                    NoteEntry.KEY_CREATED + INTEGER_TYPE + COMMA_SEP +
                    NoteEntry.KEY_EDITED + INTEGER_TYPE +
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
        values.put(NoteEntry.KEY_TITLE, note.getTitle());
        values.put(NoteEntry.KEY_CONTENT, note.getContent());
        values.put(NoteEntry.KEY_CREATED, note.getCreated().getTime());
        values.put(NoteEntry.KEY_EDITED, note.getEdited().getTime());

        db.insert(NoteEntry.TABLE_NAME,
                null,
                values);

        db.close();
    }

    public void updateEntry(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteEntry.KEY_TITLE, note.getTitle());
        values.put(NoteEntry.KEY_CONTENT, note.getContent());
        values.put(NoteEntry.KEY_EDITED, note.getEdited().getTime());

        db.update(NoteEntry.TABLE_NAME,
                values,
                NoteEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });

        db.close();
    }

    public void deleteEntry(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NoteEntry.TABLE_NAME,
                NoteEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });

        db.close();
    }

    public Note getEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NoteEntry.TABLE_NAME + " WHERE " +
                NoteEntry.KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        Note note = new Note();
        if (cursor.moveToFirst()) {
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setCreated(new Date(cursor.getLong(3)));
            note.setEdited(new Date(cursor.getLong(4)));
        }

        return note;
    }

    public List<Note> getAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NoteEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        List<Note> notes = new LinkedList<Note>();

        Note note;
        if (cursor.moveToFirst()) {
            do {
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setCreated(new Date(cursor.getLong(3)));
                note.setEdited(new Date(cursor.getLong(4)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        return notes;
    }

}
