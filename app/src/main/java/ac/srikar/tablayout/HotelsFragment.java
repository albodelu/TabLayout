package ac.srikar.tablayout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A Child Fragment class that shows Home deals in the Local Fragment class.
 */
public class HotelsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "hotels-section-number";

    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentFabDeals;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    /**
     * Initialize the root view
     */
    private View rootView;

    InitializeLocalFragment initializeHotels;

    public HotelsFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HotelsFragment newInstance(int sectionNumber) {
        HotelsFragment fragment = new HotelsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fab_deals, container, false);
        // Initialize UI elements
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        contentFabDeals = (NestedScrollView) rootView.findViewById(R.id.content_fab_deals);
        // Initialize Utility class
        prcVisibility = new ChangePRCVisibility(rootView, contentFabDeals);
//        initializeHotelsView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHotelsView();
    }

    /**
     * Method initializes Hotels fragment
     */
    private void initializeHotelsView() {
        initializeHotels = new InitializeLocalFragment(getContext(), rootView,
                coordinatorLayout, contentFabDeals, getString(R.string.hotels_category), prcVisibility);
        // Initialize Trending deals
        try {
            initializeHotels.initializeTrending(getString(R.string.hotels_merchant_name));
        } catch (Exception e) {
            Log.e("HotelsFragment", "exception initializing trending");
    }
        // Initialize Recycler view and populates data into it
        initializeHotels.initializeRV();
    }

}