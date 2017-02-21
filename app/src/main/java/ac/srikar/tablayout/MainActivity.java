package ac.srikar.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static final String LOCAL_FRAGMENT = "local-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment localFragment = new LocalFragment();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_main, localFragment, LOCAL_FRAGMENT).commit();
    }
}
