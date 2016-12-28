package com.example.library.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.PopupWindow;

/**
 * Created by riven_chris on 16/6/8.
 */
public class PopupWindowUtils {

    public static PopupWindow getPopupWindow(Activity activity, View contentView) {
        PopupWindow mPopupWindow;
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x99000000));
        mPopupWindow.setFocusable(true);
        return mPopupWindow;
    }

    public static void showPopupWindow(final PopupWindow window) {
        View view = window.getContentView();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int height = view.getMeasuredHeight();
        Animator animator = ObjectAnimator.ofFloat(view, "translationY", height, 0);
        animator.setDuration(200);
        animator.start();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottomWindow(window);
            }
        });

        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public static void showPopupAsDropDown(final PopupWindow window, View anchor) {
        View view = window.getContentView();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int height = view.getMeasuredHeight();
        Animator animator = ObjectAnimator.ofFloat(view, "translationY", -height, 0);
        animator.setDuration(200);
        animator.start();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDropDownWindow(window);
            }
        });

        window.showAsDropDown(anchor);
    }

    public static void hideBottomWindow(PopupWindow popupWindow) {
        final PopupWindow mPopupWindow = popupWindow;
        if (mPopupWindow != null) {
            View view = mPopupWindow.getContentView();
            Animator animator = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
            animator.setDuration(200);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mPopupWindow.dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    public static void hideDropDownWindow(PopupWindow popupWindow) {
        final PopupWindow mPopupWindow = popupWindow;
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public static void showPopupWindowByAlpha(final PopupWindow window) {
        View view = window.getContentView();
        AlphaAnimation anim = new AlphaAnimation(0, 1);
        anim.setDuration(500);
        anim.setFillAfter(true);
        view.startAnimation(anim);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideShareWindowByAlpha(window);
            }
        });

        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public static void hideShareWindowByAlpha(PopupWindow popupWindow) {
        final PopupWindow mPopupWindow = popupWindow;
        if (mPopupWindow != null) {
            View view = mPopupWindow.getContentView();
            AlphaAnimation anim = new AlphaAnimation(1, 0);
            anim.setDuration(500);
            anim.setFillAfter(true);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPopupWindow.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(anim);
        }
    }
}
