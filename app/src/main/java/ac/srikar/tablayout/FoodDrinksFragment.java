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
 * A Child Fragment class that shows Food and Drink deals in the Local Fragment class.
 */
public class FoodDrinksFragment extends Fragment {

    private static final String FOOD_DRINKS_SECTION_ID = "food-drinks-section-number";
    private static final int FOOD_DRINKS_SECTION_VALUE = 1;


    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentFabDeals;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    /**
     * Initialize the root view
     */
    private View rootView;

    InitializeLocalFragment initializeFoodDrinks;

    public FoodDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FoodDrinksFragment newInstance() {
        FoodDrinksFragment fragment = new FoodDrinksFragment();
        Bundle args = new Bundle();
        args.putInt(FOOD_DRINKS_SECTION_ID, FOOD_DRINKS_SECTION_VALUE);
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
//        initializeFoodDrinksView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeFoodDrinksView();
    }

    /**
     * Method initializes Food and Drinks fragment
     */
    private void initializeFoodDrinksView() {
        initializeFoodDrinks = new InitializeLocalFragment(getContext(), rootView,
                coordinatorLayout, contentFabDeals, "Food Drinks", prcVisibility);
        // Initialize Trending deals
        try {
            initializeFoodDrinks.initializeTrending(getString(R.string.food_drinks_merchant_name));
        } catch (Exception e) {
            Log.e("FoodDrinksFragment", "exception initializing trending");
        }
        // Initialize Recycler view and populates data into it
        initializeFoodDrinks.initializeRV();
    }

}