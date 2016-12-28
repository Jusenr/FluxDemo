package com.myself.fluxdemo.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.library.image.BitmapLoader;
import com.example.library.utils.ImageUtils;
import com.example.library.utils.SDCardUtils;
import com.example.library.utils.StringUtils;
import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.myself.fluxdemo.R.id.btn_save;

/**
 * Description: 我的手环(合并 我的手环、固件升级)
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/12/13 15:13.
 */
public class QRCoddeActivity extends PTWDActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.tv_text0)
    TextView mTvText0;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(btn_save)
    Button mBtnSave;
    @BindView(R.id.tv_current_version)
    TextView mTvCurrentVersion;
    @BindView(R.id.tv_latest_version)
    TextView mTvLatestVersion;
    @BindView(R.id.btn_upgrade)
    Button mBtnUpgrade;
    @BindView(R.id.tv_instructions)
    TextView mTvInstructions;
    @BindView(R.id.rl_instructions)
    RelativeLayout mRlInstructions;

    private boolean showIconQR = true;
    private boolean showInfo = true;

    private Bitmap qrBitmap;
    private String paibandSN = "P4ABCK090001";

    String avatarUrl = "http://img2.imgtn.bdimg.com/it/u=1419591888,400099843&fm=214&gp=0.jpg";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcodde;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initView();
    }

    private void initView() {
        mTvCurrentVersion.setText("当前版本  V" + 0);
        if (false) {
            mTvLatestVersion.setText("最新版本  V" + 1);
        } else {
            mTvLatestVersion.setText("当前已是最新版本");
            mTvLatestVersion.setTextColor(getResources().getColor(R.color.text_color_985EC9));
            mBtnUpgrade.setVisibility(View.GONE);
        }
    }

    /**
     * 获取手环二维码的Bitmap对象，并更新UI(如果使用中间icon，必须开启二维码容错率)
     */
    private void getQRCode() {
        if (StringUtils.isEmpty(paibandSN)) {
            mIvQrCode.setVisibility(View.GONE);
            mBtnSave.setBackgroundResource(R.drawable.btn_los_focus);
            mBtnSave.setEnabled(false);
        } else {
            qrBitmap = ImageUtils.generateBase64QRBitmap(generateQRUrl(paibandSN), 360, 360);
            if (showIconQR) {
                if (!StringUtils.isEmpty(avatarUrl)) {
                    BitmapLoader.newInstance(this).load(avatarUrl, new BitmapLoader.BitmapCallback() {
                        @Override
                        public void onResult(Bitmap bitmap) {
                            Bitmap iconBitmap = ImageUtils.zoomImg(bitmap, 60, 60);
                            qrBitmap = ImageUtils.addLogoBitmap(qrBitmap, iconBitmap);
                            mIvQrCode.setImageBitmap(qrBitmap);
                        }
                    });
                } else {
                    Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_2);
                    qrBitmap = ImageUtils.addLogoBitmap(qrBitmap, iconBitmap);
                    mIvQrCode.setImageBitmap(qrBitmap);
                }
            } else
                mIvQrCode.setImageBitmap(qrBitmap);
        }
    }

    /**
     * 保存二维码至内存卡
     */
    private void onSaveQRCode() {
        if (qrBitmap != null) {
            if (showInfo) {
                String childName = "";
                String nick_name = "花花";
                if (!StringUtils.isEmpty(nick_name))
                    childName = nick_name;
                String text = "孩子账号：" + childName + "\r\nSN:" + paibandSN;
                qrBitmap = ImageUtils.drawTextToBitmap(qrBitmap, text, getResources().getColor(R.color.text_color_985EC9), 12);
            }
            String uri = MediaStore.Images.Media.insertImage(getContentResolver(), qrBitmap, UUID.randomUUID().toString(), "手环二维码");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + uri));
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            } else {
                Uri contentUri = Uri.parse("file://" + Environment.getExternalStorageDirectory() + uri);
                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        contentUri));
            }
            if (SDCardUtils.getSDCardAllSize() > 1024 * 1024 * 5) {//>5MB
                String showText = getString(R.string.pb_save_qr_success);
                SpannableString sp = new SpannableString(showText);
                showSimpleDialog(sp);
            } else {
                String showText = getString(R.string.pb_save_qr_failed);
                int indexOf = showText.indexOf(getString(R.string.save_failed));
                SpannableString sp = new SpannableString(showText);
                sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_ED5564)), indexOf, showText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                showSimpleDialog(sp);
            }
        }
    }

    /**
     * 保存结果弹窗
     *
     * @param des
     */
    private void showSimpleDialog(SpannableString des) {
        View layout = getLayoutInflater().inflate(R.layout.layout_simple_dialog, null);
        TextView tv_des = (TextView) layout.findViewById(R.id.tv_des);
        Button btn_confirm = (Button) layout.findViewById(R.id.btn_confirm);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(layout).create();
        alertDialog.show();
        tv_des.setText(des);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public static String generateQRUrl(String sn) {
        return "http://m.putao.com/weidu?SN=" + sn + "&type=ptdevice";
    }


    @OnClick({R.id.btn_save, R.id.btn_upgrade, R.id.rl_instructions})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
            case R.id.btn_upgrade:
                break;
            case R.id.rl_instructions:
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
