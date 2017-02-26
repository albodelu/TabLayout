package ac.srikar.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

class BottomSectionsPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 4;

    private final List<String> sPageTitles = new ArrayList<String>() {
        {
            add("FAB DEALS");
            add("FOOD & DRINKS");
            add("HOTELS");
            add("OTHERS");
        }
    };

    private final List<Fragment> mPageFragments = new ArrayList<Fragment>() {
        {
            add(FabDealsFragment.newInstance());
            add(FoodDrinksFragment.newInstance());
            add(HotelsFragment.newInstance());
            add(OthersFragment.newInstance());
        }
    };

    BottomSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mPageFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        mPageFragments.set(position, createdFragment);
        return createdFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sPageTitles.get(position);
    }
}
