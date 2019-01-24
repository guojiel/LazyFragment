package com.guojiel.lazyfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guojiel.library.lazyfragment.LazyFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = findViewById(R.id.mVp);
        mVp.setOffscreenPageLimit(4);
        mVp.setAdapter(new VpAdapter());
    }

    private class VpAdapter extends FragmentPagerAdapter {

        LazyFragment fragment[] = new LazyFragment[4];

        public VpAdapter() {
            super(getSupportFragmentManager());
            for (int i = 0; i < 4; i++) {
                fragment[i] = TestFragment.newInstance(i + 1);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragment[position];
        }

        @Override
        public int getCount() {
            return fragment.length;
        }
    }

}
