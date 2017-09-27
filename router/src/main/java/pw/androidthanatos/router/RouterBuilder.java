package pw.androidthanatos.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 路由跳转类
 */

public final class RouterBuilder {

    private Class mActivityName;

    private Bundle mBundle;

    private Application mApplication;

    private int enterAnim = -1;

    private int exitAnim = -1;

    private Bundle option;

    private int requestCode = -1;


    public RouterBuilder(Application application, Class object){
        this.mActivityName = object;
        this.mApplication = application;
    }

    public RouterBuilder bundle(Bundle bundle){
        this.mBundle = bundle;
        return this;
    }


    public RouterBuilder anim(@IdRes int enter , @IdRes int exit){
        this.enterAnim = enter;
        this.exitAnim = exit;
        return this;
    }

    @RequiresApi(api = 21)
    public RouterBuilder anim(Bundle option){
        this.option = option;
        return this;
    }

    public RouterBuilder requestCode(int requestCode){
        this.requestCode = requestCode;
        return this;
    }

    public void go(@NonNull Activity activity){
        Intent intent = new Intent(activity,mActivityName);
        if (mBundle != null){
            intent.putExtras(mBundle);
        }
        check(activity,intent);
        if (this.enterAnim != -1 || this.exitAnim != -1){
            activity.overridePendingTransition(this.enterAnim,this.exitAnim);
        }
    }

    /**
     * 进行界面跳转
     * 不会执行动画操作和添加响应回调
     */
    public void go(){
        if (mActivityName == null)return;
        Intent intent = new Intent(mApplication.getApplicationContext(),mActivityName);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        if (mBundle != null){
            intent.putExtras(mBundle);
        }
        check(mApplication,intent);
    }


    /**
     * 判断跳转条件并进行跳转
     * @param context 上下文
     * @param intent  过滤器
     */
    private void check(Context context, Intent intent){

        if (context instanceof Activity){
            if (this.option == null){
                if (this.requestCode != -1){
                    ((Activity) context).startActivityForResult(intent,this.requestCode);
                }else {
                    context.startActivity(intent);
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ){
                    if (this.requestCode != -1){
                        ((Activity) context).startActivityForResult(intent,this.requestCode,this.option);
                    }else {
                        context.startActivity(intent,this.option);
                    }

                }else {
                    context.startActivity(intent);
                }
            }
        }else {
            if (this.option != null) Toast.makeText(context,"要执行动画请执行 go(Activity activity)",Toast.LENGTH_SHORT).show();
            if (this.requestCode != -1) Toast.makeText(context,"要执行页面数据回调请执行 go(Activity activity)",Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
    }

}
