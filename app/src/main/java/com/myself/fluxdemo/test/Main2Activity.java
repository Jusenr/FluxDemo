package com.myself.fluxdemo.test;

import android.os.Bundle;
import android.widget.TextView;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class Main2Activity extends PTWDActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick(R.id.tv_text)
    public void onClick() {
        mTvText.setText("你好！");
    }
}
