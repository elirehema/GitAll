package azaa.android.com.azaa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import azaa.android.com.azaa.fragments.fragmentFour;
import azaa.android.com.azaa.fragments.fragmentOne;
import azaa.android.com.azaa.fragments.fragmentThree;
import azaa.android.com.azaa.fragments.fragmentTwo;


public class viewerPager extends FragmentPagerAdapter {


    public viewerPager(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new fragmentOne();

        } else if (position == 2) {
            return new fragmentThree();

        } else if (position == 3) {
            return new fragmentFour();

        } else if (position == 1) {
            return new fragmentTwo();
        } else
            return new fragmentOne();
    }

    @Override
    public int getCount() {
        return 4;

    }


}
