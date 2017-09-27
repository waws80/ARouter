package pw.androidthanatos.router;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created on 2017/9/27.
 *  by liuxiongfei
 */
@SuppressWarnings("All")
public final class Bind {

    public static final HashMap<Integer,Object[]> CACHE = new HashMap<>();

    public static void bind(Activity activity){

        Log.d("BindView", "com.androidthanatos.annotationprocessor."+activity.getClass().getName().toString().replace(".","$")+"BindView");
        //com.androidthanatos.annotationprocessor.MainActivityBindView
        try {
            Object[] a = CACHE.get(activity.getClass().toString().hashCode());
            if (a == null){
                Finder finder = new Finder(activity);
                Class clz = Class.forName("com.androidthanatos.annotationprocessor."+activity.getClass().getName().toString().replace(".","$")+"BindView");
                Method method = clz.getMethod("bindView",new Class[]{activity.getClass(),finder.getClass()});
                Object o = clz.newInstance();
                method.invoke(o,activity,finder);
                CACHE.put(activity.getClass().getName().toString().hashCode(),new Object[]{o,activity,finder});
            }else {
                ((Method)a[0]).invoke(a[1],a[2],a[3]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> void bind(T target, View view){
        Log.d("BindView", "com.androidthanatos.annotationprocessor."+target.getClass().getName().toString().replace(".","$")+"BindView");
        //com.androidthanatos.annotationprocessor.MainActivityBindView
        try {
            Object[] a = CACHE.get(target.getClass().toString().hashCode());
            if (a == null){
                Finder finder = new Finder(view);
                Class clz = Class.forName("com.androidthanatos.annotationprocessor."+target.getClass().getName().toString().replace(".","$")+"BindView");
                Method method = clz.getMethod("bindView",new Class[]{target.getClass(),finder.getClass()});
                Object o = clz.newInstance();
                method.invoke(clz.newInstance(),target,finder);
                CACHE.put(target.getClass().getName().toString().hashCode(),new Object[]{method,o,target,finder});
            }else {
                ((Method)a[0]).invoke(a[1],a[2],a[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void unbind(Activity activity){
        ((ViewGroup)activity.getWindow().getDecorView()).removeAllViews();
    }

    public static void unbind(View view){
        ((ViewGroup)view).removeAllViews();
    }
}
