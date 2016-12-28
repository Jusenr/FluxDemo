package com.example.library.controller.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.library.R;

import butterknife.ButterKnife;


/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/10/26 18:39.
 */

public abstract class BasicDialog extends Dialog implements View.OnKeyListener {
    protected Context mContext;
    protected View mRootView;
    protected boolean cancel = false;
    private ViewGroup mMainLayout;
    private final View mBackgroundView;

    /**
     * 设置布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    public BasicDialog(Context context) {
        super(context);
        mContext = context;
//        this.setCanceledOnTouchOutside(true);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mRootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(mRootView);//设置布局
        mMainLayout = (ViewGroup) mRootView.findViewById(R.id.dialog_layout);
        ButterKnife.bind(this, mMainLayout);
        mBackgroundView = new View(mContext);
        mBackgroundView.setBackgroundColor(0xb0000000);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBackgroundView.setLayoutParams(layoutParams);

        //响应返回键
        mRootView.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK)
            dismiss();
        return false;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
