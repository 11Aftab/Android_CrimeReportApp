package come.crimeReport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class Registration extends AppCompatActivity {
    EditText et_name, et_email, et_pass, et_cnf, et_add, et_ph;
    Button btreg;
    RadioButton rb_m, rb_f;
    RadioGroup Radiogrp;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_cnf = (EditText) findViewById(R.id.et_cnf);
        et_ph = (EditText) findViewById(R.id.et_ph);
        et_add = (EditText) findViewById(R.id.et_add);
        btreg = (Button) findViewById(R.id.bt_reg);
        rb_m = (RadioButton) findViewById(R.id.rb_m);
        rb_f = (RadioButton) findViewById(R.id.rb_f);
        Radiogrp = (RadioGroup) findViewById(R.id.rb_g);


        btreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_f.isChecked()) {
                    gender = "female";
                } else if (rb_m.isChecked()) {
                    gender = "male";
                }

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String pass = et_pass.getText().toString();
                String cnf_pass = et_cnf.getText().toString();
                String phone = et_ph.getText().toString();
                String add = et_add.getText().toString();
                Registration(Constantdat.registration,name, email, pass, cnf_pass, gender, phone, add);

            }
        });
    }
    public void Registration(String mUrl, String name, String email, String pass, String cnfpass, String gender, String phone, String add) {

       /* HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", pass);
        params.put("conf_pass", cnfpass);
        params.put("gender", gender);
        params.put("phone", phone);
        params.put("address", add);*/
        mUrl=mUrl+"name="+name+"&email="+email+"&password="+pass+"&conf_pass="+cnfpass+"&gender="+gender+"&phone="+phone+"&address="+add;
        new GetDataParser(Registration.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, error,userid;
                Log.i("response", String.valueOf(response));
             if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        //error = response.getString("error_details");
                        if (success.equals("1")) {
                            Toast.makeText(Registration.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(Registration.this, Login.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(Registration.this, status, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

}
