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

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = FoodDrinksFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "others-section-number";

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
    public static OthersFragment newInstance(int sectionNumber) {
        OthersFragment fragment = new OthersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeLocalOthers();
    }

    /**
     * Method initialises Others Fragment view
     */
    private void initializeLocalOthers() {
        initializeOthers = new InitializeLocalFragment(getContext(), rootView, coordinatorLayout, contentOthers,
                getString(R.string.others_category), prcVisibility);
        // Initialize Recycler view and populates data into it
        initializeOthers.initializeRV();
    }

}