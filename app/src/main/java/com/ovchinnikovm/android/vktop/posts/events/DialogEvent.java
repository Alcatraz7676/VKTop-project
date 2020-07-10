package com.ovchinnikovm.android.vktop.posts.events;

public class DialogEvent {

    private boolean last;

    public DialogEvent(boolean last) {
        this.last = last;
    }

    public boolean isLast() {
        return last;
    }
}
