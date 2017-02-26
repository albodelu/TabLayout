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
 * A Child Fragment class that shows miscellaneous deals in the Local Fragment class.
 */
public class OthersFragment extends Fragment {

    private static final String OTHERS_SECTION_ID = "others-section-number";
    private static final int OTHERS_SECTION_VALUE = 3;

    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentOthers;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    /**
     * Initialize the root view
     */
    private View rootView;

    InitializeLocalFragment initializeOthers;


    public OthersFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OthersFragment newInstance() {
        OthersFragment fragment = new OthersFragment();
        Bundle args = new Bundle();
        args.putInt(OTHERS_SECTION_ID, OTHERS_SECTION_VALUE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_others, container, false);
        // Initialize UI elements
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        contentOthers = (NestedScrollView) rootView.findViewById(R.id.others_fragment);
        // Initialize Utility class
        prcVisibility = new ChangePRCVisibility(rootView, contentOthers);
//        initializeLocalOthers();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeLocalOthers();
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initializeOthers.notifyDatasetChanged();
//    }

    /**
     * Method initialises Others Fragment view
     */
    private void initializeLocalOthers() {
        initializeOthers = new InitializeLocalFragment(getContext(), rootView, coordinatorLayout, contentOthers,
                getString(R.string.others_category), prcVisibility);
        // Initialize Trending deals
        try {
//            initializeOthers.initializeTrending(getString(R.string.fab_deals_merchant_name));
        } catch (Exception e) {
            Log.e("FabDealsFragment", "exception initializing trending");
    }
        // Initialize Recycler view and populates data into it
        initializeOthers.initializeRV();
    }

}