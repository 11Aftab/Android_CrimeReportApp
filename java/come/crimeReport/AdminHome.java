package come.crimeReport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity  {
    Button bt_userdet,bt_missing,bt_crimeDetails,bt_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bt_userdet=(Button)findViewById(R.id.bt_userdet);
        bt_missing=(Button)findViewById(R.id.bt_missing);
        bt_crimeDetails=(Button)findViewById(R.id.bt_crimeDetails);
        bt_home=(Button)findViewById(R.id.bt_home);
        bt_userdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHome.this,UserDetails.class);
                startActivity(i);
            }
        });
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHome.this,AdminLogin.class);
                startActivity(i);
            }
        });
        bt_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHome.this,AdminMissingDetail.class);
                startActivity(i);
            }
        });
        bt_crimeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHome.this,AdminCrimeDetails.class);
                startActivity(i);
            }
        });

    }
}
