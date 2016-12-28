package com.myself.fluxdemo.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myself.fluxdemo.R;
import com.myself.fluxdemo.basic.PTWDActivity;
import com.myself.fluxdemo.flux.actions.ActionsCreator;
import com.myself.fluxdemo.flux.dispatcher.Dispatcher;
import com.myself.fluxdemo.flux.stores.MessageStore;
import com.myself.fluxdemo.flux.stores.Store;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class FluxDemoActivity extends PTWDActivity {
    @BindView(R.id.message_editor)
    EditText vMessageEditor;
    @BindView(R.id.message_view)
    TextView tv_MessageView;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private MessageStore store;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flux_demo;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initDependencies();
    }

    @OnClick({R.id.message_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_button:
                if (vMessageEditor.getText() != null) {
                    actionsCreator.sendMessage(vMessageEditor.getText().toString());
                    vMessageEditor.setText(null);
                }
                break;
        }
    }

    private void initDependencies() {
        dispatcher = Dispatcher.get();
        actionsCreator = ActionsCreator.get(dispatcher);
        store = new MessageStore();
        dispatcher.register(store);
    }


    private void render(MessageStore store) {
        tv_MessageView.setText(store.getMessage());
    }

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render(store);
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(store);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
