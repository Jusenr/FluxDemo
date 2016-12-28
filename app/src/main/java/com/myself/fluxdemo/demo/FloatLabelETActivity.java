package com.myself.fluxdemo.demo;

import android.os.Bundle;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

public class FloatLabelETActivity extends PTWDActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_float_label_et;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
