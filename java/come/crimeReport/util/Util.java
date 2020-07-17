package come.crimeReport.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.view.View;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import come.crimeReport.other.MaterialRippleLayout;

/**
 * Created by root on 12/4/16.
 */
public class Util {
    private static NetworkInfo networkInfo;
    private static int countryCode;
    static Context c = null;


    /**
     * Is there internet connection
     */

    public static void addRippleEffect(View v) {
        MaterialRippleLayout.on(v).rippleColor(Color.parseColor("#FFBEBFC7")).rippleAlpha(0.6f).rippleHover(true).create();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test for connection for WIFI
        if (networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        }

        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // test for connection for Mobile
        return networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected();

    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public JSONObject getjsonobject(String responce) {
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(responce);
        } catch (Exception e) {

        }
        return jobj;
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        return target.length() != 10 || !android.util.Patterns.PHONE.matcher(target).matches();
    }

    public static String changeAnyDateFormat(String reqdate, String dateformat, String reqformat) {
        //String	date1=reqdate;

        if (reqdate.equalsIgnoreCase("") || dateformat.equalsIgnoreCase("") || reqformat.equalsIgnoreCase(""))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        String changedate = "";
        Date dt = null;
        if (!reqdate.equals("") && !reqdate.equals("null")) {
            try {
                dt = format.parse(reqdate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //SimpleDateFormat your_format = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat your_format = new SimpleDateFormat(reqformat);
            changedate = your_format.format(dt);
        }
        return changedate;
    }
    public static void showSnakBar(final Context context, String message, final View view) {
        if (c!=context) {

            Snackbar.with(context) // context
                    .text(message) // text to display
                    .actionLabel("Try Again") // action button label
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .animation(true)
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            view.performClick();

                        }
                    })
                    .show((Activity) context);
        }
        c=context;
    }

    public static void showSnakBar(final Context context, String message) {

        if (c != context) {
            Snackbar mysnackbar = Snackbar.with(context); // context
            mysnackbar.text(message) // text to display
                    .actionLabel("Try Again") // action button label
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .animation(true)
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            Activity a = (Activity) context;
                            Intent i = a.getIntent();
                            a.overridePendingTransition(0, 0);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            a.finish();
                            a.overridePendingTransition(0, 0);
                            a.startActivity(i);

                        }
                    })
                    .show((Activity) context);
        }
        c = context;
    }


    public static String getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setLenient(false);
            String oldTime = "2017-10-03 19:25:10";
            Date oldDate = null;
            try {
                oldDate = formatter.parse(oldTime);
            } catch (ParseException e) {
                e.printStackTrace();

            }
            long oldMillis = oldDate.getTime();


            long currtime = (System.currentTimeMillis());
            long starttime = (1000 * oldMillis);
            long ms = (currtime - oldMillis);

            long totalSeconds = ms / 1000;
            long second = (int) (totalSeconds % 60);

            long totalMinutes = totalSeconds / 60;
            long minute = (int) (totalMinutes % 60);

            long totalHours = totalMinutes / 60;
            long hour = (int) (totalHours % 24);

            if (hour < 1) {
                return (minute + "min ago");
            } else{
                return (hour + "hr " + minute + "min ago");
            }
            //return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday at" + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }
    public static String encode(String value) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
