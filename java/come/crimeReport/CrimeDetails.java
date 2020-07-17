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

import org.json.JSONException;
import org.json.JSONObject;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class CrimeDetails extends AppCompatActivity {
    EditText etcrtype,et_location,et_desc;
    Button bt_reg;
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_details);
        etcrtype=(EditText)findViewById(R.id.etcrtype);
        et_location=(EditText)findViewById(R.id.et_location);
        et_desc=(EditText)findViewById(R.id.et_desc);
        bt_reg=(Button) findViewById(R.id.bt_reg);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
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

        String crimetype=etcrtype.getText().toString();
        String location=et_location.getText().toString();
        String description=et_desc.getText().toString();
        String mUrl=Constantdat.sCrimeDetails+"&crime_type="+crimetype+"&location="+location+"&description="+description+"&user_id="+ AppData.userId;
        new GetDataParser(CrimeDetails.this, mUrl, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                Log.i("respon", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {
                            Toast.makeText(CrimeDetails.this, status, Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(CrimeDetails.this, DashBoard.class);
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
       // super.onBackPressed();
         finish();


    }
    private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(CrimeDetails.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                Intent i = new Intent(CrimeDetails.this, Login.class);
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
