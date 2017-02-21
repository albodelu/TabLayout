package ac.srikar.tablayout;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Class sets a custom color for the snack bar background and simplifies the implementation
 * across other activities and fragments.
 */
public class CustomSnackBar {

    private final Context context;
    private final View coordinatorView;

    /**
     * Default constructor
     *
     * @param context         context of the activity or the fragment that initiated this class.
     * @param coordinatorView coordinator layout that will show the snack bar.
     */
    public CustomSnackBar(Context context, View coordinatorView) {
        this.context = context;
        this.coordinatorView = coordinatorView;
    }

    /**
     * Method shows the snack bar for a long time.
     *
     * @param snackMessage as a String.
     */
    public void longShow(String snackMessage) {
        // Make the snack bar
        Snackbar snackbar = Snackbar.make(coordinatorView, snackMessage, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        // Set the background color for the snack bar
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snack_color));
        // finally show the snack bar
        snackbar.show();
    }

    /**
     * Method shows the snack bar for a long time.
     *
     * @param snackMessage as a string resource
     */
    public void longShow(int snackMessage) {
        // Make the snack bar
        Snackbar snackbar = Snackbar.make(coordinatorView, snackMessage, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        // Set the background color for the snack bar
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snack_color));
        // finally show the snack bar
        snackbar.show();
    }

    /**
     * Method shows the snack bar for an indefinite time.
     *
     * @param snackMessage as string resource
     */
    public void indefiniteShow(int snackMessage) {
        // Make the snack bar
        Snackbar snackbar = Snackbar.make(coordinatorView, snackMessage, Snackbar.LENGTH_INDEFINITE);
        View snackBarView = snackbar.getView();
        // Set the background color for the snack bar
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snack_color));
        // finally show the snack bar
        snackbar.show();
    }
}
