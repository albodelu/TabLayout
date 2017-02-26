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
 * A Child Fragment class that shows Fabulous deals in the Local Fragment class.
 */
public class FabDealsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "fab-deals-section-number";

    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentFabDeals;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    /**
     * Variable has reference to the root view
     */
    private View rootView;

    InitializeLocalFragment initializeFabDeals;

    public FabDealsFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FabDealsFragment newInstance(int sectionNumber) {
        FabDealsFragment fragment = new FabDealsFragment();
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeFabDealsRV();
    }

    /**
     * Method initializes fab deals fragment
     */
    private void initializeFabDealsRV() {
        initializeFabDeals = new InitializeLocalFragment(getContext(), rootView,
                coordinatorLayout, contentFabDeals, getString(R.string.fab_deals_category), prcVisibility);
        // Initialize Trending deals
        try {
            initializeFabDeals.initializeTrending(getString(R.string.fab_deals_merchant_name));
        } catch (Exception e) {
            Log.e("FabDealsFragment", "exception initializing trending");
        }
        // Initialize Recycler view and populates data into it
        initializeFabDeals.initializeRV();
    }

}