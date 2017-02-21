package ac.srikar.tablayout;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Class changes the Progress bar, Retry Button and the layout's content visibility based on the
 * connection status to the network or the server.
 */
public class ChangePRCVisibility {

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = ChangePRCVisibility.class.getSimpleName();

    /**
     * Variable has reference to the retry text view
     */
    private final TextView retryTextView;

    /**
     * Variable has reference to a Progress Bar
     */
    private ProgressBar progressBar;

    /**
     * Variable has reference to a Retry Button
     */
    private Button retryButton;

    /**
     * Variable that has reference to the main content
     */
    private View contentMain;

    /**
     * Default Constructor for activities.
     *
     * @param activity    holds the reference to the activity.
     * @param contentMain Main Content of the layout.
     */
    public ChangePRCVisibility(Activity activity, View contentMain) {
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        retryButton = (Button) activity.findViewById(R.id.retryButton);
        retryTextView = (TextView) activity.findViewById(R.id.retry_text_view);
        this.contentMain = contentMain;
    }

    /**
     * Default Constructor for fragments.
     *
     * @param rootView    Holds reference to the rootView of the Fragment.
     * @param contentMain Main Content of the layout.
     */
    public ChangePRCVisibility(View rootView, View contentMain) {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        retryButton = (Button) rootView.findViewById(R.id.retryButton);
        retryTextView = (TextView) rootView.findViewById(R.id.retry_text_view);
        this.contentMain = contentMain;
    }

    /**
     * If the connection to the server is a success then the progress bar is removed and
     * the content is shown.
     */
    public void success() {
        Log.i(LOG_TAG, "Progress Visibility Before: " + progressBar.getVisibility());
        Log.i(LOG_TAG, "Content Visibility Before: " + contentMain.getVisibility());
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (contentMain.getVisibility() != View.VISIBLE) {
            contentMain.setVisibility(View.VISIBLE);
        }
        Log.i(LOG_TAG, "Progress Visibility After: " + progressBar.getVisibility());
        Log.i(LOG_TAG, "Content Visibility After: " + contentMain.getVisibility());
    }

    /**
     * If the user clicks the retry button then remove the retry button and show the progress bar.
     */
    public void retry() {
        if (retryButton.getVisibility() == View.VISIBLE) {
            retryButton.setVisibility(View.GONE);
        }
        if (retryTextView.getVisibility() == View.VISIBLE) {
            retryTextView.setVisibility(View.GONE);
        }
        if (progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * If the connection to the network or the server failed then remove the progress bar and show
     * the retry button.
     */
    public void failed() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (retryButton.getVisibility() != View.VISIBLE) {
            retryButton.setVisibility(View.VISIBLE);
        }
        if (retryTextView.getVisibility() != View.VISIBLE) {
            retryTextView.setVisibility(View.VISIBLE);
        }
        if (contentMain.getVisibility() == View.VISIBLE) {
            contentMain.setVisibility(View.GONE);
        }
    }

    /**
     * If the connection to the network or the server failed then remove the progress bar and show
     * the retry button. Also add the onClickListener to the Retry Button.
     */
    public void failed(final FragmentRetryClick fragmentRetryClick) {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (retryButton.getVisibility() != View.VISIBLE) {
            retryButton.setVisibility(View.VISIBLE);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentRetryClick.retryButtonClicked();
                }
            });
        }
        if (retryTextView.getVisibility() != View.VISIBLE) {
            retryTextView.setVisibility(View.VISIBLE);
        }
        if (contentMain.getVisibility() == View.VISIBLE) {
            contentMain.setVisibility(View.GONE);
        }
    }

    /**
     * Method shows progress bar and removes content
     */
    public void showProgress() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (contentMain.getVisibility() == View.VISIBLE) {
            contentMain.setVisibility(View.GONE);
        }
    }

    /**
     * Interface that notifies the fragment that the retry button was clicked
     */
    interface FragmentRetryClick {
        void retryButtonClicked();
    }
}
