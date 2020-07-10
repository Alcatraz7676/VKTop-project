package com.ovchinnikovm.android.vktop.posts.events;

public class DialogEvent {

    private boolean last;
    private Integer realmId;

    public DialogEvent(boolean last) {
        this.last = last;
    }

    public DialogEvent(boolean last, Integer realmId) {
        this.last = last;
        this.realmId = realmId;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public boolean isLast() {
        return last;
    }
}
