package ac.srikar.tablayout;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class is used to store and retrieve Selected Location inside Shared Preference variable.
 * This preference is used to restricted the deals that will displayed to the user.
 */
public class LocationPrefManager {

    // Shared Pref mode
    private static final int PRIVATE_MODE = 0;
    private final Context context;
    private SharedPreferences locationPref;

    public LocationPrefManager(Context context) {
        this.context = context;
        if (this.context != null) {
            locationPref = this.context.getSharedPreferences(Constants.LOCATION_PREF_NAME, PRIVATE_MODE);
        }
    }

    public void commit(String prefLocation, int mSelectedItem) {
        SharedPreferences.Editor locationEditor = locationPref.edit();
        locationEditor.putString(Constants.PREF_USER_LOCATION, prefLocation);
        locationEditor.putInt(Constants.PREF_USER_LOCATION_NUMBER, mSelectedItem);
        locationEditor.apply();
    }

    public String getLocationString() {
        return locationPref.getString(Constants.PREF_USER_LOCATION, context.getString(R.string.select_location_local));
    }

    public int getUserChoice() {
        return locationPref.getInt(Constants.PREF_USER_LOCATION_NUMBER, 0);
    }

}
