package ac.srikar.tablayout;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class formats the date received from the server which is in YYYY-MM-DD to a friendly user
 * readable MM dd,YYYY format.
 * <p>
 * In addition to that, class also adds currency symbols.
 */
public class FormatDateCurrency {

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = FormatDateCurrency.class.getSimpleName();

    /**
     * Method changes the date format from YYYY-MM-DD to MM dd,YYYY. This will make the date more
     * readable to the user.
     *
     * @param dateString Date received from the server.
     */
    public String formatDateForUser(String dateString) {
        // Use the SimpleDateFormat(String, Locale) constructor instead of SimpleDateFormat(String).
        // If you want to make sure the output is machine-readable in a consistent way
        // (always looks the same, regardless of the actual locale of the user),
        // you can pick Locale.US. If you do not care about machine readability,
        // you can explicitly set it to use Locale.getDefault().
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error Message: " + e.getMessage());
        }
        simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        if (date != null) {
            dateString = simpleDateFormat.format(date);
        }
        return dateString;
    }

    /**
     * Method formats the current date.
     *
     * @return currentDate Formatted current date
     */
    public String formatCurrentDate() {
        // Get current Date
        Calendar calendar = Calendar.getInstance();
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Method changes the date format from dd/MM/YYYY to YYYY-MM-DD. This will make the date more
     * readable to the server
     *
     * @param dateString Date is sent to the server.
     */
    public String formatDateForServer(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error Message: " + e.getMessage());
        }
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        if (date != null) {
            dateString = simpleDateFormat.format(date);
        }
        return dateString;
    }

    /**
     * Method adds currency symbol before the product price.
     *
     * @param currency - cost of the product as an int.
     * @return currency which is int
     */
    public String formatCurrency(int currency) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        format.setMaximumFractionDigits(0);
        return format.format(currency);
    }

    /**
     * Method adds currency symbol before the product price.
     *
     * @param currency - cost of the product as a  double.
     * @return currency which is double
     */
    public String formatCurrency(Double currency) {
        return NumberFormat.getCurrencyInstance(new Locale("hi", "IN")).format(currency);
    }
}