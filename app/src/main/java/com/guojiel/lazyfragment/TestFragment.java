package com.guojiel.lazyfragment;

import android.util.Log;
import android.widget.TextView;

import com.guojiel.library.lazyfragment.LazyFragment;

/**
 * Created by guojiel on 2019/1/16.
 *
 */
public class TestFragment extends LazyFragment {

    static String TAG = "TestFragment000";

    public static TestFragment newInstance(int position){
        TestFragment fragment = new TestFragment();
        fragment.Index = position;
        return fragment;
    }

    private int Index;

    private TextView mTv;

    @Override
    protected void lzViewCreate() {
        super.lzViewCreate();
        setContentView(R.layout.fragment_test);
        mTv = findViewById(R.id.mTv);
        mTv.setText(String.valueOf(Index));
        Log.e(TAG, Index + "->lzViewCreate: ");
    }

    @Override
    protected void lzResume() {
        super.lzResume();
        Log.e(TAG, Index + "->lzResume: " );
    }

    @Override
    protected void lzPause() {
        super.lzPause();
        Log.e(TAG, Index + "->lzPause: " );
    }

    @Override
    protected void lzViewDestroy() {
        super.lzViewDestroy();
        Log.e(TAG, Index + "->lzViewDestroy: ");
    }
}
