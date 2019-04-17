package com.ctech.shockman.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mViewPager = findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager myFragmentManger = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(myFragmentManger) {
            @Override
            public Fragment getItem(int postition) {
                Crime myCrime = mCrimes.get(postition);
                return CrimeFragment.newInstance(myCrime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
    }
}
