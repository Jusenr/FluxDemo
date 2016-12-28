package com.myself.fluxdemo.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.mvp.widgets.ILoadState;

/**
 * Created by riven_chris on 16/6/7.
 */
public class LoadStateView extends ILoadState implements View.OnClickListener {

    private View loadStateView;
    RelativeLayout rlNoData;
    RelativeLayout rlLoadFailed;
    Button btnRefresh;
    TextView tvEmptyMessage;
    ImageView emptyImage;

    public LoadStateView(Context context) {
        this(context, null);
    }

    public LoadStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadStateView = LayoutInflater.from(context).inflate(R.layout.layout_load_states, this, true);
        rlNoData = (RelativeLayout) findViewById(R.id.rl_no_data);
        rlLoadFailed = (RelativeLayout) findViewById(R.id.rl_load_failed);
        btnRefresh = (Button) findViewById(R.id.btn_no_data);
        tvEmptyMessage = (TextView) findViewById(R.id.tv_empty_message);
        emptyImage = (ImageView) findViewById(R.id.img_no_data);
        setVisibility(View.GONE);
    }

    @Override
    public void onLoadFailed() {
        setVisibility(View.VISIBLE);
        btnRefresh.setOnClickListener(this);
        rlNoData.setVisibility(View.GONE);
        rlLoadFailed.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoadSuccessEmpty() {
        setVisibility(View.VISIBLE);
        rlLoadFailed.setVisibility(View.GONE);
        rlNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadSuccess() {
        rlNoData.setVisibility(View.GONE);
        rlLoadFailed.setVisibility(View.GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void setEmptyMessage(String emptyMessage) {
        tvEmptyMessage.setText(emptyMessage);
    }

    @Override
    public void setEmptyView(@DrawableRes int drawableId) {
        emptyImage.setImageDrawable(ContextCompat.getDrawable(getContext(), drawableId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no_data:
                if (mOnRetryListener != null)
                    mOnRetryListener.onRetry(v);
                break;
        }
    }
}
