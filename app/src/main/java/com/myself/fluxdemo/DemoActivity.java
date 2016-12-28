package com.myself.fluxdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myself.fluxdemo.basic.PTWDActivity;
import com.myself.fluxdemo.demo.FloatLabelETActivity;
import com.myself.fluxdemo.demo.FloatingActionActivity;
import com.myself.fluxdemo.demo.FluxDemoActivity;
import com.myself.fluxdemo.demo.LoggerActivity;
import com.myself.fluxdemo.demo.ViewHoverActivity;
import com.myself.fluxdemo.demo.ZjcsActivity;
import com.myself.fluxdemo.demo.contextmeun.ContextMenuActivity;
import com.myself.fluxdemo.test.DiscrollViewActivity;
import com.myself.fluxdemo.test.sidemenu.SideMemuActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DemoActivity extends PTWDActivity {

    @BindView(R.id.tv_01)
    Button mTv01;
    @BindView(R.id.tv_02)
    Button mTv02;
    @BindView(R.id.tv_03)
    Button mTv03;
    @BindView(R.id.tv_04)
    Button mTv04;
    @BindView(R.id.tv_05)
    Button mTv05;
    @BindView(R.id.tv_06)
    Button mTv06;
    @BindView(R.id.tv_10)
    Button mTv10;
    @BindView(R.id.tv_11)
    Button mTv11;
    @BindView(R.id.tv_12)
    Button mTv12;
    @BindView(R.id.tv_13)
    Button mTv13;
    @BindView(R.id.tv_14)
    Button mTv14;
    @BindView(R.id.tv_15)
    Button mTv15;
    @BindView(R.id.tv_20)
    Button mTv20;
    @BindView(R.id.tv_21)
    Button mTv21;
    @BindView(R.id.tv_22)
    Button mTv22;
    @BindView(R.id.tv_23)
    Button mTv23;
    @BindView(R.id.tv_24)
    Button mTv24;
    @BindView(R.id.tv_25)
    Button mTv25;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initView();
    }

    private void initView() {
        mTv01.setText("注解测试");
        mTv02.setText("FluxDemo");
        mTv03.setText("DiscrollView");
        mTv04.setText("SideMemu");
        mTv05.setText("F-L-E-T");
        mTv06.setText("F-A-Button");
        mTv10.setText("C-Menu");
        mTv11.setText("ViewHover");


    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(LoggerActivity.class);
    }

    @OnClick({R.id.tv_01, R.id.tv_02, R.id.tv_03, R.id.tv_04, R.id.tv_05, R.id.tv_06, R.id.tv_10, R.id.tv_11, R.id.tv_12, R.id.tv_13, R.id.tv_14, R.id.tv_15, R.id.tv_20, R.id.tv_21, R.id.tv_22, R.id.tv_23, R.id.tv_24, R.id.tv_25})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_01:
                mTv01.setTextColor(Color.parseColor("#985EC9"));
                startActivity(ZjcsActivity.class);
                break;
            case R.id.tv_02:
                mTv02.setTextColor(Color.parseColor("#985EC9"));
                startActivity(FluxDemoActivity.class);
                break;
            case R.id.tv_03:
                mTv03.setTextColor(Color.parseColor("#985EC9"));
                startActivity(DiscrollViewActivity.class);
                break;
            case R.id.tv_04:
                mTv04.setTextColor(Color.parseColor("#985EC9"));
                startActivity(SideMemuActivity.class);
                break;
            case R.id.tv_05:
                mTv05.setTextColor(Color.parseColor("#985EC9"));
                startActivity(FloatLabelETActivity.class);
                break;
            case R.id.tv_06:
                mTv06.setTextColor(Color.parseColor("#985EC9"));
                startActivity(FloatingActionActivity.class);
                break;
            case R.id.tv_10:
                mTv10.setTextColor(Color.parseColor("#985EC9"));
                startActivity(ContextMenuActivity.class);
                break;
            case R.id.tv_11:
                mTv11.setTextColor(Color.parseColor("#985EC9"));
                startActivity(ViewHoverActivity.class);
                break;
            case R.id.tv_12:
                mTv12.setTextColor(Color.parseColor("#985EC9"));
                break;
            case R.id.tv_13:
                mTv13.setTextColor(Color.parseColor("#985EC9"));
                break;
            case R.id.tv_14:
                mTv14.setTextColor(Color.parseColor("#985EC9"));
                break;
            case R.id.tv_15:
                mTv15.setTextColor(Color.parseColor("#985EC9"));
                break;
            case R.id.tv_20:
                mTv20.setTextColor(Color.parseColor("#985EC9"));
                break;
            case R.id.tv_21:
                break;
            case R.id.tv_22:
                break;
            case R.id.tv_23:
                break;
            case R.id.tv_24:
                break;
            case R.id.tv_25:
                break;
        }
    }
}
