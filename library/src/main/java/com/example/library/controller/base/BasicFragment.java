package com.example.library.controller.base;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.BasicApplication;
import com.example.library.R;
import com.example.library.controller.eventbus.EventBusHelper;
import com.example.library.controller.intent.FragmentIntent;
import com.example.library.controller.view.LoadingHUD;
import com.example.library.utils.DiskFileCacheHelper;

import java.io.Serializable;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * 基础Fragment
 * Created by guchenkai on 2015/11/19.
 */
public abstract class BasicFragment<App extends BasicApplication> extends Fragment {
    private View mFragmentView = null;
    protected FragmentActivity mActivity;

    //fragment管理器
    protected FragmentManager mFragmentManager;
    protected Bundle args;//传递的参数值

    protected App mApp;
    private OkHttpClient mOkHttpClient;
    protected LoadingHUD loading;//加载框

    protected DiskFileCacheHelper mDiskFileCacheHelper;//磁盘文件缓存器

    private Fragment currentFragment;//当前Fragment
    private Fragment targetFragment;//目标Fragment

    protected boolean isVisible;//当前Fragment是否显示

    /**
     * 设置布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 布局创建完成回调
     *
     * @param savedInstanceState savedInstanceState
     */
    public abstract void onViewCreatedFinish(Bundle savedInstanceState);

    /**
     * 收集本Activity请求时的url
     *
     * @return url
     */
    protected abstract String[] getRequestUrls();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
        mApp = (App) mActivity.getApplication();
        mOkHttpClient = BasicApplication.getOkHttpClient();
        this.loading = LoadingHUD.getInstance(activity);
        loading.setSpinnerType(LoadingHUD.SIMPLE_ROUND_SPINNER);
        mDiskFileCacheHelper = mApp.getDiskFileCacheHelper();

        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        EventBusHelper.register(this);//注册EventBus
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mFragmentView = inflater.inflate(layoutId, container, false);
        ViewGroup parent = (ViewGroup) mFragmentView.getParent();
        if (parent != null)
            parent.removeView(mFragmentView);
        ButterKnife.bind(this, mFragmentView);
        return mFragmentView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedFinish(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        loading.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        loading.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentView != null)
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);//反注册EventBus
    }

    /**
     * 返回
     */
    protected void onBackPressed() {
        mActivity.onBackPressed();
    }

    /**
     * 缓存数据
     *
     * @param url    网络地址
     * @param result 数据源
     * @param <T>    数据类型
     */
    public <T extends Serializable> void cacheData(String url, T result) {
        mDiskFileCacheHelper.put(url, result);
    }

    /**
     * 获取缓存数据
     *
     * @param url 网络地址
     * @param <T> 数据类型
     */
    public <T extends Serializable> T getCacheData(String url) {
        T cacheData = (T) mDiskFileCacheHelper.getAsSerializable(url);
        return cacheData;
    }


    /**
     * 启动Fragment
     *
     * @param intent Fragment意图
     */
    private void startFragment(FragmentIntent intent) {
        currentFragment = intent.getCurrentFragment();
        Class<? extends Fragment> targetFragmentClazz = intent.getTargetFragmentClazz();
        Bundle args = intent.getExtras();
        if (args != null)
            targetFragment = Fragment.instantiate(mActivity, targetFragmentClazz.getName(), args);
        else
            targetFragment = Fragment.instantiate(mActivity, targetFragmentClazz.getName());
        switchFragment(currentFragment, targetFragment);
    }

    /**
     * 跳转Fragment
     *
     * @param targetClass 目标Fragment
     * @param args        传递参数
     */
    protected void startFragment(Class<? extends Fragment> targetClass, Bundle args) {
        FragmentIntent fragmentIntent = new FragmentIntent(this, targetClass, args);
        startFragment(fragmentIntent);
    }

    /**
     * 跳转Fragment
     *
     * @param targetClass 目标Fragment
     */
    protected void startFragment(Class<? extends Fragment> targetClass) {
        FragmentIntent fragmentIntent = new FragmentIntent(this, targetClass, null);
        startFragment(fragmentIntent);
    }

    /**
     * 跳转目标Activity
     *
     * @param targetClass 目标Activity类型
     */
    protected void startActivity(Class<? extends Activity> targetClass) {
        ((BasicFragmentActivity) mActivity).startActivity(targetClass);
    }

    /**
     * 跳转目标Activity(传递参数)
     *
     * @param targetClass 目标Activity类型
     * @param args        传递参数
     */
    public void startActivity(Class<? extends Activity> targetClass, Bundle args) {
        ((BasicFragmentActivity) mActivity).startActivity(targetClass, args);
    }

    /**
     * 隐式跳转目标Activity
     *
     * @param action 隐式动作
     */
    public void startActivity(String action) {
        ((BasicFragmentActivity) mActivity).startActivity(action);
    }

    /**
     * 隐式跳转目标Activity
     *
     * @param action 隐式动作
     */
    public void startActivity(String action, Bundle args) {
        ((BasicFragmentActivity) mActivity).startActivity(action, args);
    }

    /**
     * 启动目标Service
     *
     * @param targetClass 目标Service类型
     */
    public void startService(Class<? extends Service> targetClass) {
        ((BasicFragmentActivity) mActivity).startService(targetClass);
    }

    /**
     * 切换Fragment
     *
     * @param currentFragment 当前fragment
     * @param targetFragment  目标fragment
     */
    private void switchFragment(Fragment currentFragment, Fragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        String tag = targetFragment.getClass().getName();
        if (currentFragment != null)
            transaction.replace(R.id.fragment_container, targetFragment, targetFragment.getClass().getName()).addToBackStack(tag).commit();
        else
            transaction.add(R.id.fragment_container, targetFragment, targetFragment.getClass().getName()).commit();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
    }

    protected void onInvisible() {
    }
}
