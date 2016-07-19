package com.name.mvpboilerplate.injection.module;

import android.app.Activity;
import android.content.Context;

import com.name.mvpboilerplate.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    private Activity activity;

    @Provides
    @ActivityContext
    Context providesContext() {
        return activity;
    }
}
