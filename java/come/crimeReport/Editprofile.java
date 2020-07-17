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

import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class Editprofile extends AppCompatActivity {
    EditText  et_userid,et_name,et_email,et_phone;
    Button bt_edt;
    RadioButton rb_f,rb_m;
    String gender;
    String genders;
    String name,email,phone;
    ImageView back,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        et_userid=(EditText)findViewById(R.id.et_user_id);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone=(EditText)findViewById(R.id.et_phone);
        rb_m=(RadioButton) findViewById(R.id.rb_m);
        rb_f=(RadioButton) findViewById(R.id.rb_f);
        bt_edt=(Button) findViewById(R.id.bt_edt);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);

         name=getIntent().getExtras().getString("name");
         email=getIntent().getExtras().getString("email");
         phone=getIntent().getExtras().getString("phone");
         gender=getIntent().getExtras().getString("gender");
         et_userid.setText(AppData.userId);
         et_name.setText(name);
         et_email.setText(email);
        et_phone.setText(phone);
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

        if ( gender.equalsIgnoreCase("female")) {
            rb_f.setChecked(true);
            gender = "female";
        } else if ( gender.equalsIgnoreCase("male")) {
            rb_m.setChecked(true);
        }

        bt_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /* String editurl="http://192.168.1.2:8080/ejob/student/CARSystem/?edit_profile&user_id="+user_id+"&name="
                        +name+"+&email="+email+"&phone="+phone+"&gender="+gender;*/
                if (rb_f.isChecked()) {
                    genders = "female";
                } else if (rb_m.isChecked()) {
                    genders = "male";
                }



                String userid=et_userid.getText().toString();
                String names=et_name.getText().toString();
                String emails=et_email.getText().toString();
                String phones=et_phone.getText().toString();

                String Editurl= Constantdat.sEdit_profile+"user_id="+userid+"&name="
                        +names+"+&email="+emails+"&phone="+phones+"&gender="+genders;

                edit(Editurl);

            }
        });


    }


    public void edit(String mUrl) {
        new GetDataParser(Editprofile.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, id, name = null,email = null,gender = null,phone = null,address = null;
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {

                            Toast.makeText(Editprofile.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(Editprofile.this, Profile.class);
                            startActivity(i);



                        } else {
                            Toast.makeText(Editprofile.this, status, Toast.LENGTH_LONG).show();

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
        final AlertDialog.Builder obj=new AlertDialog.Builder(Editprofile.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                finish();
                Intent i = new Intent(Editprofile.this, DashBoard.class);
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
