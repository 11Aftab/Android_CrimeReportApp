package come.crimeReport.parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Map;

import come.crimeReport.app.AppController;
import come.crimeReport.util.Util;


public class PostDataParser {
    ProgressDialog dialog;

    private void showpDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hidepDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public PostDataParser(final Context context, String url, final Map<String, String> params, final boolean flag, final OnGetResponseListner listner) {
        if (!Util.isConnected(context)) {
            //Util.showSnakBar(context,context.getResources().getString(R.string.internectconnectionerror));
            Toast.makeText(context, "No internet connections", Toast.LENGTH_SHORT).show();
            listner.onGetResponse(null);
            return;
        }
        if (flag) {
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Util util = new Util();
                            JSONObject jobj = util.getjsonobject(response);
                            listner.onGetResponse(jobj);
                        } catch (Exception e) {
                            listner.onGetResponse(null);
                            e.printStackTrace();
                        }
                        if (flag)
                            hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (flag)
                    hidepDialog();
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                listner.onGetResponse(null);
                VolleyLog.d("Error: " + error.getMessage());
                //TastyToast.makeText(context, "Network error.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
          /*  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                if (AppData.sToken != null) {
                    headers.put("Authorization", "bearer "+AppData.sToken);
                }
                return headers;
            }*/
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public interface OnGetResponseListner {
        void onGetResponse(JSONObject response);
    }
}
