package com.ctech.shockman.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctech.shockman.criminalintent.database.CrimeBaseHelper;
import com.ctech.shockman.criminalintent.database.CrimeCursorWrapper;
import com.ctech.shockman.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ctech.shockman.criminalintent.database.CrimeDbSchema.*;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

//        for (int i = 0; i < 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime #" + i);
//            crime.setSolved(i % 2 == 0);
//            mCrimes.add(crime);
//        }
    }

    public void updateCrime(Crime c) {
        String crimeId = c.getId().toString();
        ContentValues newValues = getContentValues(c);

        String searchString = CrimeTable.Columns.UUID + " = ?";
        String[] searchArgs = new String[] { crimeId };
        mDatabase.update(CrimeTable.NAME, newValues, searchString, searchArgs);
    }

    public void addCrime(Crime c) {
        ContentValues newValues = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, newValues);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper crimeCursor = queryCrimes( null, null);

        try {
            crimeCursor.moveToFirst();
            while (crimeCursor.isAfterLast() != true) {
                Crime thisCrime = crimeCursor.getCrime();
                crimes.add(thisCrime);
                crimeCursor.moveToNext();
            }
        } finally {
            crimeCursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        String[] searchArgs = new String[] {id.toString()};
        CrimeCursorWrapper crimeCursor = queryCrimes(
                CrimeTable.Columns.UUID + " = ?", searchArgs);
        try {
            if (crimeCursor.getCount() == 0) {
                return null;
            } else {
                crimeCursor.moveToFirst();
                return crimeCursor.getCrime();
            }
        } finally {
            crimeCursor.close();
        }

    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(CrimeTable.Columns.UUID, crime.getId().toString());
        myContentValues.put(CrimeTable.Columns.TITLE, crime.getTitle());
        myContentValues.put(CrimeTable.Columns.DATE, crime.getDate().getTime());
        myContentValues.put(CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0);

        return myContentValues;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null);
        return new CrimeCursorWrapper(cursor);
    }
}
