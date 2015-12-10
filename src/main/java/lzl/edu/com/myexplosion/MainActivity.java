package lzl.edu.com.myexplosion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import lzl.edu.com.myexplosion.view.ExplosionView;

public class MainActivity extends AppCompatActivity{

    private TextView mTv;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExplosionView explosionView = new ExplosionView(this);
        explosionView.addListener(findViewById(R.id.root));
    }
}
