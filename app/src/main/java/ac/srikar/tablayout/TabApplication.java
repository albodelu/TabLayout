package ac.srikar.tablayout;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class TabApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // To get started with Fresco, initialize the Fresco class.
        Fresco.initialize(this);
    }
}
