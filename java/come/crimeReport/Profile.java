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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class Profile extends AppCompatActivity {
    //String user_id="";
    EditText et_name,et_email,et_ph,et_add;
    Button bt_cpass,bt_crimeDetails,bt_miss;
    ImageButton bt_edit;
    RadioButton rb_m,rb_f;
    String gender;
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_ph=(EditText)findViewById(R.id.et_ph);
        et_add=(EditText)findViewById(R.id.et_add);
        bt_edit=(ImageButton) findViewById(R.id.bt_edit);
        bt_cpass=(Button) findViewById(R.id.bt_cpass);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        bt_crimeDetails=(Button) findViewById(R.id.bt_crimeDetails);
        bt_miss=(Button) findViewById(R.id.bt_miss);
        rb_m=(RadioButton) findViewById(R.id.rb_m);
        rb_f=(RadioButton) findViewById(R.id.rb_f);

        String  urls=Constantdat.profile+"log_id="+AppData.userId;
        profiles(urls);
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

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rb_f.isChecked()) {
                    gender = "female";
                } else if (rb_m.isChecked()) {
                    gender = "male";
                }

                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String phone = et_ph.getText().toString().trim();
                String add = et_add.getText().toString().trim();
                Intent i = new Intent(Profile.this, Editprofile.class);
                i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("phone",phone);
                i.putExtra("gender",gender);
                startActivity(i);

            }
        });

    }

    public void profiles(String mUrl) {
        new GetDataParser(Profile.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, id, name = null,email = null,gender = null,phone = null,address = null;
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {

                            JSONArray jsonArray=response.getJSONArray("profile");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                 id=jsonObject.getString("id");
                                 name=jsonObject.getString("name");
                                 email=jsonObject.getString("email");
                                 gender=jsonObject.getString("gender");
                                 phone=jsonObject.getString("phone");
                                 address=jsonObject.getString("address");
                            }
                            //Toast.makeText(Profile.this, status, Toast.LENGTH_LONG).show();
                            et_name.setText(name);
                            et_email.setText(email);
                            if (gender.equalsIgnoreCase("female")){
                                rb_f.setChecked(true);
                            }
                            else  if (gender.equalsIgnoreCase("male")){
                                rb_m.setChecked(true);
                            }
                            et_ph.setText(phone);
                            et_add.setText(address);


                        } else {
                            Toast.makeText(Profile.this, status, Toast.LENGTH_LONG).show();  finish();
                            Intent i = new Intent(Profile.this, DashBoard.class);
                            startActivity(i);


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
        finish();

    }
    private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(Profile.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                finish();
                Intent i = new Intent(Profile.this, Login.class);
                startActivity(i);


            }
        });  obj.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);


            }
        });
        obj.show();

    }

}
