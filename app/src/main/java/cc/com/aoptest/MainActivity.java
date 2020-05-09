package cc.com.aoptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @CheckNet(value = "value")
    @Override
    public void onClick(View mView) {
        int mId = mView.getId();
        if (mId == R.id.tv) {
            Toast.makeText(MainActivity.this, "有网点击", Toast.LENGTH_SHORT).show();
        }
    }
}
