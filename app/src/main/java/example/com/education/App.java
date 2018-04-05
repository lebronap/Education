package example.com.education;

/**
 * Created by ap on 2017/6/24.
 */
import android.app.Application;

import org.xutils.x;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
    }
}
