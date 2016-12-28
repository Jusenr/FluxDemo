package com.myself.fluxdemo.test;

import android.os.Bundle;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

public class DiscrollViewActivity extends PTWDActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_discroll_view;
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
