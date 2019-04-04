package com.ctech.shockman.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;       // the crime's unique ID
    private String mTitle;  // title of the crime
    private Date mDate;     // date of the crime
    private boolean mSolved; // has the crime been solved?

    public Crime() {
        mId = UUID.randomUUID(); // automatically create a guaranteed-unique ID.
        mDate = new Date();      // default the crime date to right now
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
