package com.myself.fluxdemo.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ZjcsActivity extends PTWDActivity {

    @BindView(R.id.et_00)
    EditText mEt00;
    @BindView(R.id.tv_01)
    TextView mTv01;
    @BindView(R.id.tv_02)
    TextView mTv02;
    @BindView(R.id.tv_03)
    TextView mTv03;

    @BindString(R.string.app_name)
    String app;
    @BindColor(R.color.color_blue_green)
    int blue;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zjcs;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_01, R.id.btn_02, R.id.btn_03})
    public void onClick(View view) {
        String string = mEt00.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_01:
                mTv01.setText(app);
                mTv01.setTextColor(blue);
                break;
            case R.id.btn_02:
                mTv02.setText(string);
                break;
            case R.id.btn_03:
                ArrayList<String> strings = new ArrayList<>();
                mTv03.setText(strings.get(4));
                break;
        }
    }
}
