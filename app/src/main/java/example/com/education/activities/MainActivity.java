package example.com.education.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import example.com.education.R;
import example.com.education.adapter.ViewPagerAdapter;
import example.com.education.fragements.CenterFragment;
import example.com.education.fragements.ManagerFragment;
import example.com.education.fragements.ManagerFragment2;
import example.com.education.fragements.ManagerFragment3;
import example.com.education.fragements.MessageFragment;


public class MainActivity extends AppCompatActivity {

    private ViewPager vv_viewPager;
    private BottomNavigationView navigation;
    private List<Fragment> fragmentList;
    private Long id;
    private int roleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        roleType = preferences.getInt("roleType",0);


        vv_viewPager = (ViewPager) findViewById(R.id.vv_viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentList = new ArrayList<>();

        if (roleType == 1){
            fragmentList.add(new ManagerFragment());
            fragmentList.add(new MessageFragment());
            fragmentList.add(new CenterFragment());
       } else if (roleType ==2){
            fragmentList.add(new ManagerFragment2());
            fragmentList.add(new MessageFragment());
            fragmentList.add(new CenterFragment());
       }else {
            fragmentList.add(new ManagerFragment3());
            fragmentList.add(new MessageFragment());
            fragmentList.add(new CenterFragment());
        }

        vv_viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        vv_viewPager.addOnPageChangeListener(mOnPageChangeListener);
        vv_viewPager.setCurrentItem(0);

    }
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            navigation.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.manager:
                    vv_viewPager.setCurrentItem(0);
                    return true;
                case R.id.message:
                    vv_viewPager.setCurrentItem(1);
                    return true;
                case R.id.center:
                    vv_viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
}
