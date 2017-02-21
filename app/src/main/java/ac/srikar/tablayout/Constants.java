package ac.srikar.tablayout;

/**
 * Class that holds reference to the constants used in multiple classes.
 */
public class Constants {

    /**
     * Shared preference file that stores location name
     */
    public static final String LOCATION_PREF_NAME = "user-selected-location";

    /**
     * Preference that stores the selected location by the user in
     * {@link .LocationPrefManager}
     */
    public static final String PREF_USER_LOCATION = "userSelectedLocation";

    /**
     * Preference that stores the selected location number in the list
     * {@link .LocationPrefManager}
     */
    public static final String PREF_USER_LOCATION_NUMBER = "userChoice";

    /**
     * Name of the intent that is used to send a local broadcast to notify
     * {@link LocalFragment} to update it's contents
     */
    public static final String UPDATE_LOCAL_FRAGMENT_LOCATION_CHANGED = "update-local-fragment-location-changed";


}
