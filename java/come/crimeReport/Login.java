package come.crimeReport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class Login extends AppCompatActivity  implements View.OnClickListener {
    EditText et_email, et_pass;
    TextView admin;
    Button btlogin, btregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et_email = (EditText) findViewById(R.id.email);
        et_pass = (EditText) findViewById(R.id.pass);
        btlogin = (Button) findViewById(R.id.btlogin);
        btregister = (Button) findViewById(R.id.btregister);
        admin = (TextView) findViewById(R.id.admin);
        btlogin.setOnClickListener(this);
        btregister.setOnClickListener(this);
        admin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == btlogin) {
            String emailval = et_email.getText().toString();
            String passval = et_pass.getText().toString();
            if (!emailval.equals("") && !emailval.startsWith(" ")) {

                if (!passval.equals("") && !passval.startsWith(" ")) {

                    String url_login = Constantdat.login + "email=" + emailval + "&password=" + passval;
                    login(url_login);

                } else {
                    et_pass.setError("Enter valid password");
                    et_pass.requestFocus();
                }
            }else{
                    et_email.setError("Enter Email");
                    et_email.requestFocus();
                }

        } else if (view == btregister) {
            Intent i = new Intent(Login.this, Registration.class);
            startActivity(i);

        }

      /*  else if (view == admin) {
            Intent i = new Intent(Login.this, AdminLogin.class);
            startActivity(i);

        }
*/

    }

    public void login(String mUrl) {
        new GetDataParser(Login.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                Log.i("urlsinup", Constantdat.registration);
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        user_id=response.getString("user_id");
                        AppData.userId=user_id;
                        //error = response.getString("error_details");
                        if (success.equals("1")) {
                            Toast.makeText(Login.this, status, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Login.this, DashBoard.class);
                            //i.putExtra("user_id",user_id);
                            startActivity(i);

                        } else {
                            Toast.makeText(Login.this, status, Toast.LENGTH_LONG).show();

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



