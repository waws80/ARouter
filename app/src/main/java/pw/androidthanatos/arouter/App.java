package pw.androidthanatos.arouter;

import android.app.Application;

import pw.androidthanatos.router.ARouter;

/**
 * Created by liuxiongfei on 2017/9/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
