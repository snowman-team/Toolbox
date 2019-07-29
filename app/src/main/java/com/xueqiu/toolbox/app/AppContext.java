package com.xueqiu.toolbox.app;

import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("PrivateApi")
public class AppContext {

    public static Application INSTANCE;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (final Exception e) {
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ignored) {

            }
        } finally {
            INSTANCE = app;
        }
    }

}
