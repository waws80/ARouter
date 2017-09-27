package pw.androidthanatos.router;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 路由器
 */
@SuppressWarnings("All")
public final class ARouter {

    /**
     * 全局的路由注册表
     */
    private static HashMap<String, Class> mGloabMap;

    private static WeakReference<Application> mWeakReference;

    /**
     * 初始化路由器
     */
    public static  void init(Application application){
        mWeakReference = new WeakReference<>(application);
        try {
            Class routerHelper = Class.forName("com.androidthanatos.annotationprocessor.RouterHelper");
            Method method = routerHelper.getMethod("routerTabs",new Class[]{});
            mGloabMap = (HashMap<String, Class>) method.invoke(routerHelper,new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 构建 RouterBuilder
     * @param alias 目标activity的别名，若没指定别名但使用了 @BindAlias() 注解 则填入目标activity的类名
     *              eg：目标类为： MainActivity 则填入 MainActivity字符串即可
     * @return RouterBuilder
     */
    public static RouterBuilder build(String alias){
        if (mWeakReference == null) throw new RuntimeException("请在Application中初始化Router");
        if (mGloabMap == null) throw new RuntimeException("请在Application中初始化Router");
        if (mGloabMap.isEmpty()) throw new IllegalArgumentException("请对当前工程的activity添加注解");

        Class name = mGloabMap.get(alias);
        if (name == null){
            Toast.makeText(mWeakReference.get(),"没有找到当前以"+alias+"为别名的Activity",
                    Toast.LENGTH_SHORT).show();

        }
        return new RouterBuilder(mWeakReference.get(),name);
    }
}
