package ac.srikar.tablayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

/**
 * Class is used to create an Async Task and get the details from the server
 */
public class PrimaryAsyncTask {

    /**
     * Log cat Tag
     */
    private static final String LOG_TAG = PrimaryAsyncTask.class.getSimpleName();

    private final Context context;
    private final CoordinatorLayout coordinatorLayout;
    private final View contentMain;
    private AsyncResponseForFragment asyncResponseForFragment;

    // Variables has reference to utility class
    private ChangePRCVisibility prcVisibility;
    private CustomSnackBar snackBar;

    /**
     * Reference to the Async Http Client
     */
    private AsyncHttpClient client;

    /**
     * Default Constructor for fragments.
     *
     * @param context                  reference to the initiated activity's or fragment's context.
     * @param rootView                 Holds reference to the rootView of the Fragment.
     * @param coordinatorLayout        reference to the activity's parent coordinator view group.
     * @param contentMain              reference to the views main content.
     * @param asyncResponseForFragment reference the {@link AsyncResponseForFragment} interface
     */
    public PrimaryAsyncTask(Context context, CoordinatorLayout coordinatorLayout, View rootView,
                            View contentMain, AsyncResponseForFragment asyncResponseForFragment) {
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        this.contentMain = contentMain;
        this.asyncResponseForFragment = asyncResponseForFragment;
        // Initialize the utility classes
        prcVisibility = new ChangePRCVisibility(rootView, this.contentMain);
        snackBar = new CustomSnackBar(this.context, this.coordinatorLayout);
    }

    /**
     * Method cancels the  pending (or potentially active) requests associated with the passed Context.
     */
    public void cancelPrimaryAsyncTask() {
        Log.i(LOG_TAG, "Client: " + client);
        if (client != null) {
            client.cancelAllRequests(true);
        }
    }

    /**
     * Method executes the async task and get the JSON data as a string. Method used for fragments.
     *
     * @param extraUrl that gets appended to the server Url
     */
    public void executeFragment(String extraUrl) {
        // Retrieve data from the server
        if (userOnlineFragment()) {
            client = new AsyncHttpClient();
            // Finalize the url to retrieve necessary data
            String finalUrl = context.getString(R.string.server_ip_address) + extraUrl;
            Log.i(LOG_TAG, "Final Url: " + finalUrl);
            client.get(finalUrl, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    String stringResponse = new String(response);
                    Log.i(LOG_TAG, "Server Response: " + stringResponse);
                    if (!stringResponse.isEmpty()) {
                        try {
                            asyncResponseForFragment.primaryAsyncSuccess(stringResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(LOG_TAG, "JSON Exception: ", e);
                            prcVisibility.failed(new ChangePRCVisibility.FragmentRetryClick() {
                                @Override
                                public void retryButtonClicked() {
                                    prcVisibility.retry();
                                    asyncResponseForFragment.primaryAsyncFailed();
                                }
                            });
                            snackBar.longShow(context.getString(R.string.conflict_data_server));
                        }
                    } else {
                        prcVisibility.failed(new ChangePRCVisibility.FragmentRetryClick() {
                            @Override
                            public void retryButtonClicked() {
                                prcVisibility.retry();
                                asyncResponseForFragment.primaryAsyncFailed();
                            }
                        });
                        //snackBar.longShow(context.getString(R.string.server_sent_empty_data));
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    prcVisibility.failed(new ChangePRCVisibility.FragmentRetryClick() {
                        @Override
                        public void retryButtonClicked() {
                            prcVisibility.retry();
                            asyncResponseForFragment.primaryAsyncFailed();
                        }
                    });
                    //snackBar.longShow(R.string.server_connection_failed);
                    Log.i(LOG_TAG, "Failed to connect with the server: " + ": " + e.getMessage());
                }
            });
        }
    }

    /**
     * Method checks whether the user is online or not. Method Used for fragments.
     */
    private boolean userOnlineFragment() {
        NetworkInfo netInfo = null;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } else {
            Log.i(LOG_TAG, "Context is null");
        }
        if (netInfo != null && netInfo.isConnected()) {
            Log.i(LOG_TAG, "Connected to Internet");
            return true;
        } else {
            Log.i(LOG_TAG, "Not Connected to Internet");
            prcVisibility.failed(new ChangePRCVisibility.FragmentRetryClick() {
                @Override
                public void retryButtonClicked() {
                    prcVisibility.retry();
                    asyncResponseForFragment.primaryAsyncFailed();
                }
            });
            //snackBar.indefiniteShow(R.string.cannot_connect_network);
            return false;
        }
    }

    /**
     * Creates an interface between this class and the initiated activity or the fragment. Interface
     * is used for fragments.
     * <p>
     * Interface is implemented when the async task is completed successfully and the server
     * response (if it is not empty) is shared.
     */
    public interface AsyncResponseForFragment {
        void primaryAsyncSuccess(String stringResponse) throws JSONException;

        void primaryAsyncFailed();
    }
}
