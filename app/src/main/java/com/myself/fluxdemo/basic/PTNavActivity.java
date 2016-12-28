package com.myself.fluxdemo.basic;

import android.widget.EditText;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.mvp.base.IPresenter;
import com.myself.fluxdemo.mvp.base.PTActivity;
import com.myself.fluxdemo.widgets.NavigationBar;

import butterknife.BindView;

/**
 * 包含navigation_bar的MVP架构基础Activity
 * Created by zsw on 2016/7/6.
 */
public abstract class PTNavActivity<P extends IPresenter> extends PTActivity<P> implements NavigationBar.ActionsListener {
    @BindView(R.id.navigation_bar)
    public NavigationBar navigation_bar;

    /**
     * 添加标题栏
     */
    protected void addNavigation() {
        navigation_bar.setActionListener(this);
    }

    /**
     * 设置主标题文字
     *
     * @param text 标题文字
     */
    protected void setMainTitle(String text) {
        navigation_bar.setMainTitle(text);
    }

    /**
     * 设置主标题文字颜色
     *
     * @param color
     */
    protected void setMainTitleColor(int color) {
        navigation_bar.setMainTitleColor(color);
    }

    /**
     * 设置左标题文字
     *
     * @param text 标题文字
     */
    protected void setLeftTitle(String text) {
        navigation_bar.setLeftTitle(text);
    }

    /**
     * 设置左标题文字颜色
     *
     * @param color 颜色id
     */
    protected void setLeftTitleColor(int color) {
        navigation_bar.setLeftTitleColor(color);
    }

    /**
     * 设置左标题文字是否可以点击
     *
     * @param isClick 是否可以点击
     */
    protected void setLeftClickable(boolean isClick) {
        navigation_bar.setLeftClickable(isClick);
    }

    /**
     * 设置左标题是否显示
     *
     * @param visibility 是否显示
     */
    protected void setLeftVisibility(boolean visibility) {
        navigation_bar.setLeftVisibility(visibility);
    }

    /**
     * 设置右标题文字
     *
     * @param text 标题文字
     */
    protected void setRightTitle(String text) {
        navigation_bar.setRightTitle(text);
    }

    /**
     * 设置右标题文字颜色
     *
     * @param color 颜色id
     */
    protected void setRightTitleColor(int color) {
        navigation_bar.setRightTitleColor(color);
    }

    /**
     * 设置右标题文字是否可以点击
     *
     * @param isClick 是否可以点击
     */
    protected void setRightClickable(boolean isClick) {
        navigation_bar.setRightClickable(isClick);
    }

    @Override
    public void onLeftAction() {
//        onBackPressed();
        finish();
    }

    @Override
    public void onRightAction() {

    }

    @Override
    public void onMainAction() {

    }

    @Override
    public void setEditable(EditText mEdit, int maxLength) {
        super.setEditable(mEdit, maxLength);
    }

}
