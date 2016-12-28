package com.myself.fluxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.library.controller.base.BasicFragmentActivity;
import com.example.library.utils.FileUtils;
import com.example.library.utils.Logger;
import com.example.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.myself.fluxdemo.bean.FirInfoBean;
import com.myself.fluxdemo.demo.AppInfoActivity;
import com.myself.fluxdemo.test.wavetextview.Titanic;
import com.myself.fluxdemo.test.wavetextview.TitanicTextView;
import com.myself.fluxdemo.test.wavetextview.Typefaces;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * Flux的Controller-View模块
 * Created by Jusenr on 18/12/15.
 */
public class MainActivity extends BasicFragmentActivity {

    @BindView(R.id.tv_name)
    TitanicTextView mTvName;

    private FirInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        getFirAppVersionInfo();

        mTvName.setTypeface(Typefaces.get(this));// set fancy typeface
        new Titanic().start(mTvName);// start animation
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.main_title, R.id.left_title, R.id.right_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_title:
                break;
            case R.id.left_title:
                startActivity(new Intent(this, DemoActivity.class));
                break;
            case R.id.right_title:
                if (null != mBean) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppInfoActivity.APP_INFO, mBean);
                    startActivity(AppInfoActivity.class, bundle);
                } else
                    ToastUtils.showToastShort(this, "获取应用信息失败！");
                break;
        }
    }

    @OnLongClick(R.id.iv_img)
    public boolean onLongClick() {
        getFirAppVersionInfo();
        return false;
    }

    /**
     * Fir获取版本信息测试(FIR)
     */
    public void getFirAppVersionInfo() {
        FIR.checkForUpdateInFIR(TotalApplication.FIR_API_TOKEN, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.i("FIR", "check from fir.im success! " + "\n" + versionJson);

                mBean = new Gson().fromJson(versionJson, FirInfoBean.class);
                String fsize = FileUtils.convertFileSize(mBean.getBinary().getFsize());
                String installUrl = mBean.getInstallUrl();
                String version = mBean.getVersion();
                String changelog = mBean.getChangelog();
                String versionShort = mBean.getVersionShort();

                Logger.d("fsize=" + fsize + "\ninstallUrl=" + installUrl + "\nversion=" + version + "\nversionShort=" + versionShort + "\nchangelog=" + changelog);
            }

            @Override
            public void onFail(Exception exception) {
                Log.i("FIR", "check fir.im fail! " + "\n" + exception.getMessage());
                Toast.makeText(getApplicationContext(), R.string.not_network_try_again, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                if (null != mBean)
                    Toast.makeText(getApplicationContext(), "当前版本：" + mBean.getVersionShort(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return exit();
        }
        return super.onKeyDown(keyCode, event);
    }
}
