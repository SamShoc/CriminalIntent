package com.ctech.shockman.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ctech.shockman.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.ctech.shockman.criminalintent.database.CrimeDbSchema.*;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Columns.UUID));
        String title = getString(getColumnIndex(CrimeTable.Columns.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Columns.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Columns.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved == 1);

        return crime;
    }
}
