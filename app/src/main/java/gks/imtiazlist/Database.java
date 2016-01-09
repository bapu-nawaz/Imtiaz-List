package gks.imtiazlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

    private static final String TAG = "SQLiteDatabase";
    public static final String
        ROWID = "_id",
        NAME 	= "name",
        CHECK 	= "check",
        DESC	= "description";

    public static final String[]
        KEYS   = new String[] {ROWID, NAME, DESC, CHECK};

    public static final String
        DATABASE_NAME   = "MyDb",
        TABLE 	= "Checklist";

    public static final int DATABASE_VERSION = 1;

    private static final String
        CREATE_TABLE =
            "create table " + TABLE
            + " (" + ROWID + " integer primary key autoincrement, "
            + NAME + " text not null, "
            + DESC + " text not null, "
            + CHECK + " integer);";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public Database(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }
		
    public Database open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
		
    public void close() {
			myDBHelper.close();
		}

    public long insert(String name, String desc){
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(DESC, desc);
        return db.insert(TABLE, null, values);
    }

    private void delete(long rowId){
        String where = ROWID + "=" + rowId;
        db.delete(TABLE, where, null);
    }

    public void deleteAll() {
        Cursor i = getAll();
        if (i.moveToFirst()) {
            do {
                delete(i.getLong(0));
            } while (i.moveToNext());
        }
        i.close();
    }

    public Cursor getAll() {
        String where = null;
        Cursor c = 	db.query(true, TABLE, KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean update(long rowId, String name, String desc) {
        String where = ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(NAME, name);
        newValues.put(DESC, desc);
        return db.update(TABLE, newValues, where, null) != 0;
    }
    public boolean update(long rowId, int check) {
        String where = ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(CHECK, check);
        return db.update(TABLE, newValues, where, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(_db);
        }
    }
}
