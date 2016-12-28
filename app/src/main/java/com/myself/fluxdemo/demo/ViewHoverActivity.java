package com.myself.fluxdemo.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.library.androidviewhover.BlurLayout;
import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import butterknife.BindView;

public class ViewHoverActivity extends PTWDActivity {

    @BindView(R.id.source)
    ImageView mSource;
    @BindView(R.id.blur_layout)
    BlurLayout mBlurLayout;
    @BindView(R.id.source2)
    ImageView mSource2;
    @BindView(R.id.blur_layout2)
    BlurLayout mBlurLayout2;
    @BindView(R.id.source3)
    ImageView mSource3;
    @BindView(R.id.blur_layout3)
    BlurLayout mBlurLayout3;
    @BindView(R.id.source4)
    ImageView mSource4;
    @BindView(R.id.blur_layout4)
    BlurLayout mBlurLayout4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_hover;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        refreshView();
    }

    private void refreshView() {
        BlurLayout.setGlobalDefaultDuration(450);
        View hover = LayoutInflater.from(mContext).inflate(R.layout.hover_sample, null);
        hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada)
                        .duration(550)
                        .playOn(v);
            }
        });
        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Swing)
                        .duration(550)
                        .playOn(v);
            }
        });
        mBlurLayout.setHoverView(hover);
        mBlurLayout.setBlurDuration(550);
        mBlurLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
        mBlurLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
        mBlurLayout.addChildAppearAnimator(hover, R.id.more, Techniques.FlipInX, 550, 500);

        mBlurLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
        mBlurLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
        mBlurLayout.addChildDisappearAnimator(hover, R.id.more, Techniques.FlipOutX, 550, 0);

        mBlurLayout.addChildAppearAnimator(hover, R.id.description, Techniques.FadeInUp);
        mBlurLayout.addChildDisappearAnimator(hover, R.id.description, Techniques.FadeOutDown);

        //sample 2
        View hover2 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample2, null);
        hover2.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Pretty Cool, Right?", Toast.LENGTH_SHORT).show();
            }
        });
        mBlurLayout2.setHoverView(hover2);
        mBlurLayout2.addChildAppearAnimator(hover2, R.id.description, Techniques.FadeInUp);
        mBlurLayout2.addChildDisappearAnimator(hover2, R.id.description, Techniques.FadeOutDown);
        mBlurLayout2.addChildAppearAnimator(hover2, R.id.avatar, Techniques.DropOut, 1200);
        mBlurLayout2.addChildDisappearAnimator(hover2, R.id.avatar, Techniques.FadeOutUp);
        mBlurLayout2.setBlurDuration(1000);

        //sample3
        View hover3 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample3, null);
        mBlurLayout3.setHoverView(hover3);
        mBlurLayout3.addChildAppearAnimator(hover3, R.id.eye, Techniques.Landing);
        mBlurLayout3.addChildDisappearAnimator(hover3, R.id.eye, Techniques.TakingOff);
        mBlurLayout3.enableZoomBackground(true);
        mBlurLayout3.setBlurDuration(1200);

        //sample 4
        View hover4 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample4, null);
        mBlurLayout4.setHoverView(hover4);
        mBlurLayout4.addChildAppearAnimator(hover4, R.id.cat, Techniques.SlideInLeft);
        mBlurLayout4.addChildAppearAnimator(hover4, R.id.mail, Techniques.SlideInRight);

        mBlurLayout4.addChildDisappearAnimator(hover4, R.id.cat, Techniques.SlideOutLeft);
        mBlurLayout4.addChildDisappearAnimator(hover4, R.id.mail, Techniques.SlideOutRight);

        mBlurLayout4.addChildAppearAnimator(hover4, R.id.content, Techniques.BounceIn);
        mBlurLayout4.addChildDisappearAnimator(hover4, R.id.content, Techniques.FadeOutUp);

        hover4.findViewById(R.id.cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia"));
                startActivity(getWebPage);
            }
        });

        hover4.findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daimajia@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About AndroidViewHover");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I have a good idea about this project..");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
