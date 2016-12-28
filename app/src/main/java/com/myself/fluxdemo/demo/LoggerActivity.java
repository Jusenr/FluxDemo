package com.myself.fluxdemo.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.myself.fluxdemo.TotalApplication;
import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import butterknife.BindView;

public class LoggerActivity extends PTWDActivity {
    public static final String BUNDLE_CRASH_LOG = "bundle_crash_log";

    @BindView(R.id.tv_crash_log)
    TextView tv_crash_log;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logger;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        tv_crash_log.setText(TotalApplication.showCrash());
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
