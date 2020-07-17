package come.crimeReport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppController;
import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class AdminLogin extends AppCompatActivity {
    EditText et_email, et_pass;
    Button btlogin, btregister;
   // String url="http://192.168.1.2:8080/ejob/student/CARSystem/admin.php?admin_login&admin_email=admin@gmail.com&admin_password=admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        et_email = (EditText) findViewById(R.id.email);
        et_pass = (EditText) findViewById(R.id.pass);
        btlogin = (Button) findViewById(R.id.btlogin);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailvals = et_email.getText().toString();
                String passval = et_pass.getText().toString();

               if (!emailvals.equals("") && !emailvals.startsWith(" ")) {

                    if (!passval.equals("") && !passval.startsWith(" ")) {


                        login(emailvals,passval);

                      /* finish();

                        Intent i = new Intent(AdminLogin.this, AdminHome.class);
                        startActivity(i);*/

                    } else {
                        et_pass.setError("Enter valid password");
                        et_pass.requestFocus();
                    }
                }else{
                    et_email.setError("Enter Email");
                    et_email.requestFocus();
                }
                //String url_login = "http://103.230.103.142:8080//ejob/student/CARSystem/admin.php?admin_login&admin_email=admin@gmail.com&admin_password=admin";
                //login();

            }
        });
    }

  /*  public void adminlogin(String url){
        JsonArrayRequest jsonObjectRequest=new JsonArrayRequest(Request.Method.GET, url,null,new  )
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }*/

    public void login( String email,String pass) {
        final String url_login = "http://103.230.103.142:8080//ejob/student/CARSystem/admin.php?admin_login&admin_email=admin@gmail.com&admin_password=admin";
        //final String url_login = "http://192.168.1.2:8080//ejob/student/CARSystem/admin.php?admin_login&admin_email="+email+"&admin_password="+pass;

        new GetDataParser(AdminLogin.this, url_login, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status;
                Log.i("urlsinupadmin", url_login);
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {
                            Toast.makeText(AdminLogin.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(AdminLogin.this, AdminHome.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(AdminLogin.this, status, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
