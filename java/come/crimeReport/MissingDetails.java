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
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class MissingDetails extends AppCompatActivity {
     EditText et_name,et_age,et_hight,et_lastseen,et_des,et_add,et_ph;
    RadioButton rb_m,rb_f;
    Button bt_reg;
    String gender;
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_details);
        et_name=(EditText)findViewById(R.id.et_name);
        et_age=(EditText)findViewById(R.id.et_age);
        et_hight=(EditText)findViewById(R.id.et_hight);
        et_lastseen=(EditText)findViewById(R.id.et_lastseen);
        et_des=(EditText)findViewById(R.id.et_des);
        et_add=(EditText)findViewById(R.id.et_add);
        et_ph=(EditText)findViewById(R.id.et_ph);
        bt_reg=(Button) findViewById(R.id.bt_reg);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        rb_m=(RadioButton) findViewById(R.id.rb_m);
        rb_f=(RadioButton) findViewById(R.id.rb_f);

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
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missingDetails();
            }
        });
    }

    public void missingDetails() {

        if (rb_f.isChecked()) {
            gender = "Female";
        } else if (rb_m.isChecked()) {
            gender = "Male";
        }
        String name=et_name.getText().toString();
        String age=et_age.getText().toString();
        String height=et_hight.getText().toString();
        String lastseen=et_lastseen.getText().toString();
        String description=et_des.getText().toString();
        String add=et_add.getText().toString();
        String phone=et_ph.getText().toString();
        String mUrl=Constantdat.sMissing_details+"name="+name+"&age="+age+"&gender="+gender+"&height="+height+"&lastseen="+lastseen+"&description="+description+"&address="+add+"&phone="+phone+"&user_id="+ AppData.userId;
        new GetDataParser(MissingDetails.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                Log.i("urlsinup", Constantdat.registration);
                Log.i("respon", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {
                            finish();
                            Intent i = new Intent(MissingDetails.this, DashBoard.class);
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

    } private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(MissingDetails.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                Intent i = new Intent(MissingDetails.this, Login.class);
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
