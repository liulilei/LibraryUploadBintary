package test.library.libraryuploadbintary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.library.libraryuploadbintrary.AppUtils;
import test.library.libraryuploadbintrary.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.show(this, AppUtils.getAppVersionCode(this) + "");

    }
}
