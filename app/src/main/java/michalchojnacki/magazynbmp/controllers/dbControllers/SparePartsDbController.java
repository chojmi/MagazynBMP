package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

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

    public boolean saveSparePart(SparePart sparePart) {
        if (!doesSparePartExist(sparePart)) {
            SQLiteDatabase db = mFavDbHelper.getWritableDatabase();
            ContentValues values = getContentValues(sparePart);
            db.insert(SparePartsDbEntry.TABLE_NAME, null, values);
            db.close();
            return true;
        }
        return false;
    }

    private boolean doesSparePartExist(SparePart sparePart) {
        SQLiteDatabase db = mFavDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(SparePartsDbCommands.sqlCheckEntry(new SparePart.Builder().number(sparePart.getNumber()).build()), null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    @NonNull
    private ContentValues getContentValues(SparePart sparePart) {
        ContentValues values = new ContentValues();
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER, sparePart.getNumber());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION, sparePart.getDescription());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE, sparePart.getType());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER, sparePart.getProducer());
        values.put(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_LOCATION, sparePart.getLocation());
        return values;
    }

    public SparePart[] findSparePart(SparePart searchedSparePart) {
        ArrayList<SparePart> spareParts = new ArrayList<>();
        SQLiteDatabase db = mFavDbHelper.getReadableDatabase();

        String sqlCheckEntry = SparePartsDbCommands.sqlCheckEntry(searchedSparePart);
        if (sqlCheckEntry != null) {
            Cursor cursor = db.rawQuery(sqlCheckEntry, null);

            if (cursor.getCount() <= 0) {
                cursor.close();
                db.close();
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    spareParts.add(getSparePart(cursor));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return spareParts.toArray(new SparePart[spareParts.size()]);
        } else {
            return null;
        }
    }

    @NonNull
    private SparePart getSparePart(Cursor cursor) {
        return new SparePart.Builder()
                .number(cursor.getString(2))
                .description(cursor.getString(1))
                .type(cursor.getString(3))
                .location(cursor.getString(5))
                .producer(cursor.getString(4))
                .build();
    }

    public void deleteAllSpareParts() {
        SQLiteDatabase db = mFavDbHelper.getWritableDatabase();
        db.delete(SparePartsDbEntry.TABLE_NAME, null, null);
        db.close();
    }

    private static abstract class SparePartsDbEntry implements BaseColumns {

        public static final String COLUMN_NAME_SPARE_PART_NUMBER = "sparePartNumber";
        public static final String COLUMN_NAME_SPARE_PART_DESCRIPTION = "sparePartDescription";
        public static final String COLUMN_NAME_SPARE_PART_TYPE = "sparePartType";
        public static final String COLUMN_NAME_SPARE_PART_LOCATION = "sparePartLocation";
        public static final String COLUMN_NAME_SPARE_PART_PRODUCER = "sparePartProducer";
        private static final String TABLE_NAME = "spareParts";
    }

    private static abstract class SparePartsDbCommands {

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + SparePartsDbEntry.TABLE_NAME + " (" +
                        SparePartsDbEntry._ID + " INTEGER PRIMARY KEY," +
                        SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER + TEXT_TYPE + COMMA_SEP +
                        SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE + TEXT_TYPE + COMMA_SEP +
                        SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER + TEXT_TYPE + COMMA_SEP +
                        SparePartsDbEntry.COLUMN_NAME_SPARE_PART_LOCATION + TEXT_TYPE +
                        ")";

        private static final String SQL_READ_ENTRIES = "SELECT  * FROM " + SparePartsDbEntry.TABLE_NAME;

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SparePartsDbEntry.TABLE_NAME;

        private static String sqlCheckEntry(SparePart sparePart) {
            if (sparePart.getDescription() == null && sparePart.getNumber() == null && sparePart.getType() == null && sparePart.getProducer() == null)
                return null;

            boolean needsAnd = false;
            StringBuilder builder = new StringBuilder()
                    .append(SQL_READ_ENTRIES)
                    .append(" WHERE ");

            if (sparePart.getNumber() != null) {
                builder.append(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_NUMBER + " LIKE '%")
                        .append(sparePart.getNumber())
                        .append("%'");
                needsAnd = true;
            }

            if (sparePart.getDescription() != null) {
                if (needsAnd) {
                    builder.append(" AND ");
                }
                builder.append(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_DESCRIPTION + " LIKE '%")
                        .append(sparePart.getDescription())
                        .append("%'");
                needsAnd = true;
            }

            if (sparePart.getType() != null) {
                if (needsAnd) {
                    builder.append(" AND ");
                    needsAnd = true;
                }
                builder.append(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_TYPE + " LIKE '%")
                        .append(sparePart.getType())
                        .append("%'");
            }

            if (sparePart.getProducer() != null) {
                if (needsAnd) {
                    builder.append(" AND ");
                }
                builder.append(SparePartsDbEntry.COLUMN_NAME_SPARE_PART_PRODUCER + " LIKE '%")
                        .append(sparePart.getProducer())
                        .append("%'");
            }

            return builder.toString();
        }
    }

    private class FavDbHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 1;
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