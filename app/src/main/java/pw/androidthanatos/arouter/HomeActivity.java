package pw.androidthanatos.arouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thanatos.BindAlias;
import com.thanatos.BindView;
import com.thanatos.BindLayout;

import pw.androidthanatos.router.Bind;

@BindAlias("home")
@BindLayout(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bind.bind(this);
        String info = getIntent().getStringExtra("info");
        if (info != null) textView.setText(info);
        Intent i = new Intent();
        i.putExtra("data","你好，main");
        setResult(101,i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bind.unbind(this);

    }
}
