package ac.srikar.tablayout;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A Child Fragment class that shows Food and Drink deals in the Local Fragment class.
 */
public class FoodDrinksFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "food-drinks-section-number";

    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentFabDeals;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    /**
     * Initialize the root view
     */
    private View rootView;

    public FoodDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FoodDrinksFragment newInstance(int sectionNumber) {
        FoodDrinksFragment fragment = new FoodDrinksFragment();
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
        initializeFoodDrinksView();
        return rootView;
    }

    /**
     * Method initializes Food and Drinks fragment
     */
    private void initializeFoodDrinksView() {
        InitializeLocalFragment initializeFoodDrinks = new InitializeLocalFragment(getContext(), rootView,
                coordinatorLayout, contentFabDeals, "Food Drinks", prcVisibility);
        // Initialize Trending deals
        initializeFoodDrinks.initializeTrending(getString(R.string.food_drinks_merchant_name));
        // Initialize Recycler view and populates data into it
        initializeFoodDrinks.initializeRV();
    }

}