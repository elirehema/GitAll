package azaa.android.com.azaa.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import azaa.android.com.azaa.ui.fragments.fragmentFour;
import azaa.android.com.azaa.ui.fragments.fragmentOne;
import azaa.android.com.azaa.ui.fragments.fragmentThree;
import azaa.android.com.azaa.ui.fragments.fragmentTwo;


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
