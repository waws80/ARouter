package pw.androidthanatos.router;

import android.app.Activity;
import android.view.View;

/**
 * Created by liuxiongfei on 2017/9/27.
 */

public class Finder {

    private Activity activity;

    private View view;

    public Finder(Activity activity){
        this.activity = activity;
    }

    public Finder(View view){
        this.view = view;
    }


    public Activity getActivity(){
        return activity;
    }


    public View findView(){
        if (activity != null){
            return activity.getWindow().getDecorView();
        }else {
            return view;
        }

    }



}
