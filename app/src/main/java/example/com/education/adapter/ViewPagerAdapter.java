package example.com.education.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ap on 2017/6/23.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentlist;

    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentlist) {
        super(fm);
        this.fragmentlist = fragmentlist;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
}
