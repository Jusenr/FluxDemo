package com.myself.fluxdemo.demo;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;

import com.example.library.floatingaction.AddFloatingActionButton;
import com.example.library.floatingaction.FloatingActionButton;
import com.example.library.floatingaction.FloatingActionsMenu;
import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FloatingActionActivity extends PTWDActivity {

    @BindView(R.id.setter)
    FloatingActionButton mSetter;
    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @BindView(R.id.action_a)
    FloatingActionButton mActionA;
    @BindView(R.id.action_b)
    FloatingActionButton mActionB;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu mMultipleActions;
    @BindView(R.id.button_remove)
    FloatingActionButton mButtonRemove;
    @BindView(R.id.button_gone)
    FloatingActionButton mButtonGone;
    @BindView(R.id.multiple_actions_down)
    FloatingActionsMenu mMultipleActionsDown;
    @BindView(R.id.setter_drawable)
    FloatingActionButton mSetterDrawable;
    @BindView(R.id.semi_transparent)
    AddFloatingActionButton mSemiTransparent;
    @BindView(R.id.normal_plus)
    AddFloatingActionButton mNormalPlus;
    @BindView(R.id.multiple_actions_left)
    FloatingActionsMenu mMultipleActionsLeft;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_floating_action;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        refreshView();
    }

    private void refreshView() {
        mSetter.setSize(FloatingActionButton.SIZE_MINI);
        mSetter.setColorNormalResId(R.color.pink);
        mSetter.setColorPressedResId(R.color.pink_pressed);
        mSetter.setIcon(R.drawable.ic_fab_star);
        mSetter.setStrokeVisible(false);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionB.setVisibility(mActionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        mMultipleActions.addButton(actionC);

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));
        mSetterDrawable.setIconDrawable(drawable);

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        mButtonGone.setVisibility(View.GONE);

        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Added once");
        mRightLabels.addButton(addedOnce);

        FloatingActionButton addedTwice = new FloatingActionButton(this);
        addedTwice.setTitle("Added twice");
        mRightLabels.addButton(addedTwice);
        mRightLabels.removeButton(addedTwice);
        mRightLabels.addButton(addedTwice);

    }

    @OnClick({R.id.pink_icon, R.id.action_a, R.id.action_b, R.id.button_remove, R.id.action_enable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pink_icon:
                mSetterDrawable.setVisibility(mSetterDrawable.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.action_a:
                mActionA.setTitle("Action A clicked");
                break;
            case R.id.action_b:
                mActionB.setTitle("Action B clicked");
                break;
            case R.id.button_remove:
                mMultipleActionsDown.removeButton(mButtonRemove);
                break;
            case R.id.action_enable:
                mMultipleActions.setEnabled(!mMultipleActions.isEnabled());
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
