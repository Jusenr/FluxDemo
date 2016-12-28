package com.myself.fluxdemo.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.example.library.utils.AppUtils;
import com.example.library.utils.DateUtils;
import com.example.library.utils.FileUtils;
import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;
import com.myself.fluxdemo.bean.FirInfoBean;

import butterknife.BindView;

public class AppInfoActivity extends PTWDActivity {
    public static final String APP_INFO = "APP_INFO";

    @BindView(R.id.tv_appname)
    TextView tv_appname;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_versionShort)
    TextView tv_versionShort;
    @BindView(R.id.tv_fsize)
    TextView tv_fsize;
    @BindView(R.id.tv_updatetime)
    TextView tv_updatetime;
    @BindView(R.id.tv_changelog)
    TextView tv_changelog;
    @BindView(R.id.tv_update_url)
    TextView tv_update_url;
    @BindView(R.id.tv_installUrl)
    TextView tv_installUrl;

    private FirInfoBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        String versionName = AppUtils.getVersionName(mContext);
        setRightTitle(versionName);
        mBean = (FirInfoBean) args.getSerializable(APP_INFO);

        initView();
    }

    private void initView() {
        tv_appname.setText(mBean.getName());
        tv_version.setText(mBean.getBuild());
        tv_versionShort.setText(mBean.getVersionShort());
        tv_fsize.setText(FileUtils.convertFileSize(mBean.getBinary().getFsize()));
        tv_updatetime.setText(DateUtils.millisecondToDate(mBean.getUpdated_at(), DateUtils.YMD_HMS_PATTERN));
        tv_changelog.setText(mBean.getChangelog());
        tv_update_url.setText(mBean.getUpdate_url());
        tv_installUrl.setText(mBean.getInstallUrl());
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
