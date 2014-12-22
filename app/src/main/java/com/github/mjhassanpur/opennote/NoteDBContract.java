package com.github.mjhassanpur.opennote;

import android.provider.BaseColumns;


public final class NoteDBContract {

    public NoteDBContract() {}

    public static abstract class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String KEY_ID = "id";
        public static final String KEY_CONTENT = "content";
    }
}
