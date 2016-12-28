package com.myself.fluxdemo.flux.stores;

import com.myself.fluxdemo.flux.actions.Action;
import com.squareup.otto.Bus;

/**
 * Flux的Store模块
 * Created by ntop on 18/12/15.
 */
public abstract class Store {
    private  static final Bus bus = new Bus();

    protected Store() {
    }

    public void register(final Object view) {
        this.bus.register(view);
    }

    public void unregister(final Object view) {
        this.bus.unregister(view);
    }

    void emitStoreChange() {
        this.bus.post(changeEvent());
    }

    public abstract StoreChangeEvent changeEvent();
    public abstract void onAction(Action action);

    public class StoreChangeEvent {}
}
