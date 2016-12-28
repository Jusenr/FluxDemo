package com.myself.fluxdemo.mvp.widgets;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.controller.base.BasicDialog;
import com.myself.fluxdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/10/26 18:14.
 */

public class SelectDialog extends BasicDialog implements View.OnClickListener {

    @BindView(R.id.tv_des)
    public TextView tv_des;
    @BindView(R.id.ll_tab)
    public LinearLayout ll_tab;
    @BindView(R.id.ll_first)
    LinearLayout ll_first;
    @BindView(R.id.iv_first)
    public ImageView iv_first;
    //    public ImageDraweeView iv_first;
    @BindView(R.id.tv_first)
    public TextView tv_first;
    @BindView(R.id.ll_second)
    public LinearLayout ll_second;
    @BindView(R.id.iv_second)
    public ImageView iv_second;
    //    public ImageDraweeView iv_second;
    @BindView(R.id.tv_second)
    public TextView tv_second;
    @BindView(R.id.tv_ok)
    public TextView tv_ok;

    public boolean okVisibility = false;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_confirmation;
    }

    public SelectDialog(Context context) {
        super(context);
    }

    public SelectDialog(Context context, boolean isOKVisibility) {
        super(context);
        setOKVisibility(isOKVisibility);
    }

    public SelectDialog(Context context, String descText, int descTextColor, String firstText, int firstTextColor, int firstResId, String firstUrl, String secondText, int secondTextColor, String okText, int okTextColor, int secondResId, String secondUrl) {
        super(context);
        ll_tab.setVisibility(View.VISIBLE);
        tv_des.setText(descText);
        tv_des.setTextColor(descTextColor);
        tv_first.setText(firstText);
        tv_first.setTextColor(firstTextColor);
//        iv_first.setImageResource(firstResId);
//        iv_first.setImageURL(firstUrl);
        tv_second.setText(secondText);
        tv_second.setTextColor(secondTextColor);
//        iv_second.setImageResource(secondResId);
//        iv_second.setImageURL(secondUrl);
        tv_ok.setText(okText);
        tv_ok.setTextColor(okTextColor);
//
//        if (!StringUtils.isEmpty(firstUrl) || firstResId != -1)
//            iv_first.setVisibility(View.VISIBLE);
//        if (!StringUtils.isEmpty(secondUrl) || secondResId != -1)
//            iv_second.setVisibility(View.VISIBLE);

        //切换选择键  true 显示一条(一行)  false  显示两条(一行)
        setOKVisibility(okVisibility);
    }

    protected void setOKVisibility(boolean isOKVisibility) {
        tv_ok.setVisibility(isOKVisibility ? View.VISIBLE : View.GONE);
        ll_tab.setVisibility(isOKVisibility ? View.GONE : View.VISIBLE);
    }


    @OnClick({R.id.ll_first, R.id.ll_second, R.id.tv_des, R.id.tv_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
                onLeftClick(v);
                break;
            case R.id.ll_second:
                onRightClick(v);
                break;
            case R.id.tv_des:
                onDescClick(v);
                break;
            case R.id.tv_ok:
                onOkClick(v);
                break;
        }
        dismiss();
    }

    /**
     * 点击ok
     *
     * @param v view
     */
    public void onOkClick(View v) {

    }

    /**
     * 点击left
     *
     * @param v view
     */
    public void onLeftClick(View v) {

    }

    /**
     * 点击right
     *
     * @param v view
     */
    public void onRightClick(View v) {

    }

    /**
     * 点击Desc
     *
     * @param v view
     */
    public void onDescClick(View v) {

    }
}
