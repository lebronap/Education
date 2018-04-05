package example.com.education.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import example.com.education.R;


public class SplishActivity extends AppCompatActivity {
 //   private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);

     //   SQLiteDatabase db = dbHelper.getWritableDatabase();

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 startActivity(new Intent(SplishActivity.this,LoginActivity.class));
                 finish();
            }
        },3000);
    }
}
