### <center> ARouter

##### 一个集路由框架和绑定布局和控件的框架。

###### 路由框架采用的是路由表查找的形式进行管理，控件绑定采用的是类似ButterKnife的注解形式写的，都是用的是apt注解处理器对注解进行处理，摒弃反射带来的内存消耗。

#### ARouter的使用
[![](https://jitpack.io/v/waws80/ARouter.svg)](https://jitpack.io/#waws80/ARouter) 
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)]()
[![](https://img.shields.io/badge/作者-waws80-%23ff69b4.svg)](http://androidthanatos.pw)
##### setup1：
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

##### setup2：
```
dependencies {
        compile 'com.github.waws80:ARouter:v1.0'
}

```
##### 路由器的使用：
```
@BindAlias("main")
@BindAlias() //不写默认是类名
public class MainActivity extends BaseActivity {
	......
}
```
```
ARouter.build("home")//目标界面的别名
        .bundle(bundle)//携带的bundle参数
        .requestCode(100)//写请求码代表着使用startActivityForResult()
        .anim(enterAnimId,exitAnimId)//5.0以下的动画设置(不是5.0向下兼容，是系统的)
        .anim(ActivityOptions.
                makeSceneTransitionAnimation(this,btn,"bt")
                .toBundle())//5.0以上的动画设置
        .go(this);//开始执行页面跳转 不加参数动画效果无效，添加的请求码无效
```
##### 绑定控件使用

```
//布局绑定
@BindLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

 	@BindView(R.id.button)
    public Button btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bind.bind(this);//绑定控件
        btn.setText("bind");
    }
	......
	@Override
    protected void onDestroy() {
        super.onDestroy();
        Bind.unbind(this);//解绑控件
    }
}
```

```
//View绑定
public class ViewHolder{

    @BindView(R.id.tv_item)
    public TextView textView;

    public ViewHolder(Context context){
        View view = View.inflate(context, R.layout.item,null);
        Bind.bind(this,view);//绑定View
    }

}

```
