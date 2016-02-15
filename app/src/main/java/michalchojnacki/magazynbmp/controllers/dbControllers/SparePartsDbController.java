package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import michalchojnacki.magazynbmp.model.SparePart;

public final class SparePartsDbController {

    private final FavDbHelper mFavDbHelper;

    public SparePartsDbController(Context context) {
        mFavDbHelper = new FavDbHelper(context);
    }

    public int getCountOfSpareParts() {
        SQLiteDatabase db = mFavDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SparePartsDbCommands.SQL_READ_ENTRIES, null);
        int quantity = cursor.getCount();
        cursor.close();
        db.close();
        return quantity;
    }

    public boolean saveSparePart(SparePart sparePart, boolean overwrite) {
        if (saveSparePart(sparePart)) {
            return true;
        } else if (overwrite) {
            SQLiteDatabase db = mFavDbHelper.getWritableDatabase();
            ContentValues values = getContentValues(sparePart);
            db.update(SparePartsDbEntry.TABLE_NAME, values, SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER + "='" + sparePart.getNumber() + "'", null);
            db.close();
            return true;
        }
        return false;
    }

    public boolean saveSparePart(SparePart sparePart) {
        if (!sparePartExist(sparePart)) {
            SQLiteDatabase db = mFavDbHelper.getWritableDatabase();
            ContentValues values = getContentValues(sparePart);
            db.insert(SparePartsDbEntry.TABLE_NAME, null, values);
            db.close();
            return true;
        }
        return false;
    }

    @NonNull
    private ContentValues getContentValues(SparePart sparePart) {
        ContentValues values = new ContentValues();
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER, sparePart.getNumber());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION, sparePart.getDescription());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE, sparePart.getType());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER, sparePart.getProducer());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_LOCATION, sparePart.getLocation());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_SUPPLIER, sparePart.getSupplier());
        return values;
    }

    private boolean sparePartExist(SparePart sparePart) {
        SQLiteDatabase db = mFavDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(SparePartsDbCommands.sqlCheckEntry(new SparePart.Builder().number(sparePart.getNumber()).build()), null);
        if (CursorUnderZero(db, cursor)) {
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    private boolean CursorUnderZero(SQLiteDatabase db, Cursor cursor) {
        if (cursor.getCount() <= 0) {
            cursor.close();
            db.close();
            return true;
        }
        return false;
    }

    public SparePart[] findSparePart(SparePart searchedSparePart) {
        String sqlCheckEntry = SparePartsDbCommands.sqlCheckEntry(searchedSparePart);
        return getSpareParts(sqlCheckEntry);
    }

    private SparePart[] getSpareParts(String sqlCheckEntry) {
        if (sqlCheckEntry != null) {
            SQLiteDatabase db = mFavDbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sqlCheckEntry, null);
            return getSpareParts(db, cursor);
        } else {
            return null;
        }
    }

    @Nullable
    private SparePart[] getSpareParts(SQLiteDatabase db, Cursor cursor) {
        if (CursorUnderZero(db, cursor)) {
            return null;
        }
        SparePart[] spareParts = getSpareParts(cursor);
        cursor.close();
        db.close();
        return spareParts;
    }

    @NonNull
    private SparePart[] getSpareParts(Cursor cursor) {
        ArrayList<SparePart> spareParts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                spareParts.add(getSparePart(cursor));
            } while (cursor.moveToNext());
        }
        return spareParts.toArray(new SparePart[spareParts.size()]);
    }

    @NonNull
    private SparePart getSparePart(Cursor cursor) {
        return new SparePart.Builder()
                .number(cursor.getString(2))
                .description(cursor.getString(1))
                .type(cursor.getString(3))
                .location(cursor.getString(5))
                .producer(cursor.getString(4))
                .supplier(cursor.getString(6))
                .build();
    }

    public SparePart[] findSparePart(String searchedText) {
        String sqlCheckEntry = SparePartsDbCommands.sqlCheckEntry(searchedText);
        return getSpareParts(sqlCheckEntry);
    }

    public void deleteAllSpareParts() {
        SQLiteDatabase db = mFavDbHelper.getWritableDatabase();
        db.delete(SparePartsDbEntry.TABLE_NAME, null, null);
        db.close();
    }

    private class FavDbHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 2;
        private static final String DB_NAME = "bazaY.db";

        public FavDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SparePartsDbCommands.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SparePartsDbCommands.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}