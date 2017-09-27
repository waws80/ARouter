package pw.androidthanatos.arouter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.thanatos.BindAlias;
import com.thanatos.BindLayout;
import com.thanatos.BindView;


import pw.androidthanatos.router.ARouter;
import pw.androidthanatos.router.Bind;

@BindAlias("main")
@BindLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {


    @BindView(R.id.button)
    public Button btn;
    @BindView(R.id.swp)
    public ScrollChildSwipeRefreshLayout swp;
    @BindView(R.id.sv)
    public ScrollView  sv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void onResume() {
        super.onResume();
        Bind.bind(this);
        btn.setText("bind");
    }

    @RequiresApi(21)
    public void go(View view){
        Bundle bundle = new Bundle();
        bundle.putString("info","我来自MainActivity");
        ARouter.build("home")
                .bundle(bundle)
                .requestCode(100)
                .anim(ActivityOptions.makeSceneTransitionAnimation(this,btn,"bt").toBundle())
                .go(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100  && resultCode == 101){
            Toast.makeText(this,"回调：resultCode："+resultCode+"\n data: "+data.getStringExtra("data"),Toast.LENGTH_SHORT).show();
        }
    }


    public class ViewHolder{

        @BindView(R.id.tv_item)
        public TextView textView;

        public ViewHolder(Context context){
            View view = View.inflate(context, R.layout.item,null);
            Bind.bind(this,view);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bind.unbind(this);
    }
}
