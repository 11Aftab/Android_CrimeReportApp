package come.crimeReport;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import come.crimeReport.app.AppController;
import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class Changepassword extends AppCompatActivity {

    Button btsave;
    String user_id = "";
    EditText et_op, et_new_pass;
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        user_id = getIntent().getExtras().getString("user_id");
        et_op = (EditText) findViewById(R.id.et_op);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        btsave = (Button) findViewById(R.id.btsave);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();


            }
        });
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String oldpassen=null;
                String newpaasen = null;
                String User_id = null;
                String oldpass = et_op.getText().toString();
                String newpass = et_new_pass.getText().toString();
                String userid = AppData.userId;

             /*   try {
                    oldpassen = URLEncoder.encode(oldpass,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    newpaasen = URLEncoder.encode(newpass,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    User_id = URLEncoder.encode(userid,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

               // String encodedurl=null;

                changepassword();

              /*  try {
                   // String url = "http://192.168.1.2:8080/ejob/student/CARSystem/?change_password&user_id=" + userid + "&old_pass=" + oldpass + "$new_pass=" + newpass;
                    String url = "http://103.230.103.142:8080/ejob/student/CARSystem/?change_password&user_id=" + userid + "&old_pass=" + oldpass + "$new_pass=" + newpass;
                    encodedurl = URLEncoder.encode(url, "UTF-8");
                    Log.d("TEST", encodedurl);
                } catch (UnsupportedEncodingException e) {
                   */
              //e.printStackTrace();
                }
               // String url ="http://192.168.1.2:8080/ejob/student/CARSystem/?change_password&user_id=" + User_id + "&old_pass=" + oldpassen + "$new_pass=" + newpaasen;

               /// changepass(encodedurl);
                //changepassword();
               //changepassword();
            //}


        });

    }

    public void changepassword() {

        String oldpass = et_op.getText().toString();
        String newpass = et_new_pass.getText().toString();
        String userid = AppData.userId;


        String mUrl = Constantdat.sChangePassword+"change_password&user_id=" + userid + "&old_pass=" + oldpass + "&new_pass=" + newpass;
        new GetDataParser(Changepassword.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status;
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {

                            Toast.makeText(Changepassword.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(Changepassword.this, Login.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Changepassword.this, status, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
    public void changepass( String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String success, status;
                Log.i("resp", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {

                            Toast.makeText(Changepassword.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(Changepassword.this, Login.class);
                            startActivity(i);


                        } else {
                            Toast.makeText(Changepassword.this, status, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();

    }
    private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(Changepassword.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                finish();
                Intent i = new Intent(Changepassword.this, Login.class);
                startActivity(i);


            }
        });
        obj.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);


            }
        });
        obj.show();

    }
}
