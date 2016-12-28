package com.myself.fluxdemo.flux.stores;

import com.myself.fluxdemo.flux.actions.Action;
import com.myself.fluxdemo.flux.actions.MessageAction;
import com.myself.fluxdemo.flux.model.Message;
import com.squareup.otto.Subscribe;

/**
 * MessageStore类主要用来维护MainActivity的UI状态
 * Created by ntop on 18/12/15.
 */
public class MessageStore extends Store {
    private static MessageStore singleton;
    private Message mMessage = new Message();

    public MessageStore() {
        super();
    }

    public String getMessage() {
        return mMessage.getMessage();
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case MessageAction.ACTION_NEW_MESSAGE:
                mMessage.setMessage((String) action.getData());
                break;
            default:
        }
        emitStoreChange();
    }


    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }
}
