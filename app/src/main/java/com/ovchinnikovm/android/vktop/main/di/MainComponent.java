package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
