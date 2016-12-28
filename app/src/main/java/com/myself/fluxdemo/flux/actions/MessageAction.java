package com.myself.fluxdemo.flux.actions;

/**
 * Created by ntop on 18/12/15.
 */
public class MessageAction extends Action<String> {
    public static final String ACTION_NEW_MESSAGE = "new_message";

    MessageAction(String type, String data) {
        super(type, data);
    }
}
