package ac.srikar.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * {@link FragmentStatePagerAdapter}
 */
public class BottomSectionsPagerAdapter extends FragmentStatePagerAdapter {

    BottomSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a EditProfilePlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return FabDealsFragment.newInstance(position + 1);
            case 1:
                return FoodDrinksFragment.newInstance(position + 1);
            case 2:
                return HotelsFragment.newInstance(position + 1);
            case 3:
                return OthersFragment.newInstance(position + 1);
            default:
                return FabDealsFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "FAB DEALS";
            case 1:
                return "FOOD & DRINKS";
            case 2:
                return "HOTELS";
            case 3:
                return "OTHERS";
        }
        return null;
    }
}
