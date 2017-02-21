package ac.srikar.tablayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

/**
 * Class that is used to create the location dialog shown in the Local Fragment and its child fragment.
 * <p>
 * The user then can select his/her preferred location.
 */
public class LocationDialog {

    // Context
    private final Context context;
    // Alert dialog builder variable
    private AlertDialog.Builder builder;

    // Where we track the selected items
    private int mSelectedItem = 0;
    // Preference Manager that store and gets the stored location.
    private LocationPrefManager locationPrefManager;

    /**
     * Default Constructor.
     *
     * @param context of the activity or the hosted fragment.
     */
    public LocationDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(this.context);
        locationPrefManager = new LocationPrefManager(this.context);
    }

    /**
     * Create and show the Alert dialog.
     */
    public void show(final Button buttonId, final String[] locationChoicesArray) {
        // Set the dialog title
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mSelectedItem = i;
            }
        };
        builder.setTitle(R.string.pick_location_title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(locationChoicesArray, locationPrefManager.getUserChoice(), listener)
                // Set the action for the positive button
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        locationPrefManager.commit(locationChoicesArray[mSelectedItem], mSelectedItem);
                        buttonId.setText(locationChoicesArray[mSelectedItem]);
                        // Notify the fragment and update the details
                        Intent updateLocalFragmentIntent = new Intent(Constants.UPDATE_LOCAL_FRAGMENT_LOCATION_CHANGED);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(updateLocalFragmentIntent);
                    }
                })
                // Set the action for the negative button
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the builder
        builder.create();
        // Show the builder
        builder.show();
    }

    /**
     * Restore the user location from the Shared Preference file.
     */
    public String restoreUserLocation() {
        // Restore preferences
        return locationPrefManager.getLocationString();
    }
}
