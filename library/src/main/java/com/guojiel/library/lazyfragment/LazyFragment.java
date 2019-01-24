package com.guojiel.library.lazyfragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by guojiel on 2019/1/16.
 * ViewPager 中Fragment懒加载方案
 * 使用方法 重写{@link #lzViewCreate()}在方法中调用{@link #setContentView(View)}或{@link #setContentView(int)}设置布局Layout
 * 生命周期方法
 * {@link #lzViewCreate()}
 * {@link #lzViewDestroy()}
 * {@link #lzResume()}
 * {@link #lzPause()}
 * 说明：
 * ViewPager中AFragment、BFragment,
 * 首次进入A或B执行生命周期方法：{@link #lzViewCreate()} -> {@link #lzResume()}
 * 由A切换到B执行生命周期方法：A{@link #lzPause()} -> B{@link #lzResume()}
 * 当前A是Resume状态，启动一个Activity，执行生命周期方法：A{@link #lzPause()} B不变，
 *          再回来时，执行生命周期方法：A{@link #lzResume()} B不变
 */
public class LazyFragment extends Fragment {

    private FrameLayout mRootView;

    private boolean mIsLzViewCreated;
    private boolean mIsLzResume;
    private boolean mIsLzPause;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView = configRootView(inflater, container);
    }

    protected FrameLayout configRootView(LayoutInflater inflater, ViewGroup container){
        return (FrameLayout) inflater.inflate(R.layout.lazy_fragment_root_def, container, false);
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getUserVisibleHint()) {
            setUserVisibleHintImpl(true, true);
        }
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if(mIsLzViewCreated) {
            lzViewDestroy();
            mIsLzViewCreated = false;
            mRootView = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint() && mIsLzViewCreated){
            lzPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint() && mIsLzViewCreated){
            lzResume();
        }
    }

    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setUserVisibleHintImpl(isVisibleToUser, false);
    }

    private void setUserVisibleHintImpl(boolean isVisibleToUser, boolean byOnViewCreatedMethod){
        if(mRootView == null){
            return;
        }
        if(isVisibleToUser && !mIsLzViewCreated){
            mIsLzViewCreated = true;
            lzViewCreate();
        }
        if(!mIsLzViewCreated){
            return;
        }
        if(isVisibleToUser){
            if(!byOnViewCreatedMethod) {
                lzResume();
            }
        }else{
            lzPause();
        }
    }

    protected void lzViewCreate(){
    }

    public final boolean isLzCreate(){
        return mIsLzViewCreated;
    }

    @CallSuper
    protected void lzViewDestroy(){
        setContentView(null);
    }
    @CallSuper
    protected void lzResume(){
        mIsLzResume = true;
        mIsLzPause = false;
    }

    public final boolean isLzResume(){
        return mIsLzResume;
    }

    @CallSuper
    protected void lzPause(){
        mIsLzResume = false;
        mIsLzPause = true;
    }

    public final boolean isLzPause(){
        return mIsLzPause;
    }

    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T extends View> T findViewById(int id) {
        if(mRootView == null){
            return null;
        }
        return mRootView.findViewById(id);
    }

    protected void setContentView(int layoutId){
        if(!mIsLzViewCreated){
            return;
        }
        resetContentView();
        if(layoutId == 0){
            return;
        }
        View.inflate(mRootView.getContext(), layoutId, mRootView);
    }

    protected void setContentView(View view){
        if(!mIsLzViewCreated){
            return;
        }
        resetContentView();
        if(view == null){
            return;
        }
        mRootView.addView(view, new FrameLayout.LayoutParams(-1, -1));
    }

    private void resetContentView(){
        int childCount = mRootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mRootView.getChildAt(i);
            if(child.getId() == R.id.mLzLoadingView){
                continue;
            }
            mRootView.removeView(child);
        }
    }

}
