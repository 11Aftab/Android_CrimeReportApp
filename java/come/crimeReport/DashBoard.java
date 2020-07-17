package come.crimeReport;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import come.crimeReport.app.AppData;

public class DashBoard extends AppCompatActivity {
    ImageView back,logout;
    Button bt_cpass,bt_crimeDetails,bt_miss,bt_crimeList,bt_missList,bt_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        bt_cpass=(Button) findViewById(R.id.bt_cpass);
        bt_crimeDetails=(Button) findViewById(R.id.bt_crimeDetails);
        bt_miss=(Button) findViewById(R.id.bt_miss);
        bt_crimeList=(Button) findViewById(R.id.bt_crimeList);
        bt_missList=(Button) findViewById(R.id.bt_missList);
        bt_profile=(Button) findViewById(R.id.bt_profile);

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
        bt_cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, Changepassword.class);
                i.putExtra("user_id", AppData.userId);
                startActivity(i);

            }
        });
        bt_crimeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, CrimeDetails.class);

                startActivity(i);

            }
        });
        bt_miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, MissingDetails.class);
                startActivity(i);

            }
        });
        bt_crimeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, CrimeList.class);
                startActivity(i);

            }
        });
        bt_missList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, MissingList.class);
                startActivity(i);

            }
        });
        bt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoard.this, Profile.class);
                startActivity(i);

            }
        });

    }

     private  void showdialog(){
         final AlertDialog.Builder obj=new AlertDialog.Builder(DashBoard.this);
         obj.setTitle("Logout ? ");
         obj.setTitle("Do You Want To Logout ");
         obj.setCancelable(false);
         obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 obj.setCancelable(true);
                 Intent i = new Intent(DashBoard.this, Login.class);
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
