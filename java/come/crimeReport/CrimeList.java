package come.crimeReport;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class CrimeList extends AppCompatActivity {
    ListView lv;
    ArrayList<CrimeSetget> arrayList = new ArrayList<>();
    ImageView back, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);
        lv = (ListView) findViewById(R.id.lv);
        back = (ImageView) findViewById(R.id.back);
        logout = (ImageView) findViewById(R.id.logout);
        String url = Constantdat.sgetCrimeDetailsList+"user_id="+ AppData.userId;
        crimedetails(url);
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
    }

    public void crimedetails(String url) {
        new GetDataParser(CrimeList.this, url, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {


                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CrimeSetget crimeSetget = new CrimeSetget();
                                JSONObject jobj = jsonArray.getJSONObject(i);

                                crimeSetget.setId(jobj.getString("id"));
                                crimeSetget.setCrime_type(jobj.getString("crime_type"));
                                crimeSetget.setLocation(jobj.getString("location"));
                                crimeSetget.setDescription(jobj.getString("description"));
                                crimeSetget.setStatus(jobj.getString("status"));
                                arrayList.add(crimeSetget);
                            }
                            lv.setAdapter(new MyAdap());

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    class MyAdap extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inf = getLayoutInflater();
            View row = inf.inflate(R.layout.crimecontent, null);
            TextView tvctype = (TextView) row.findViewById(R.id.tvctype);
            TextView tvlocation = (TextView) row.findViewById(R.id.tvlocation);
            TextView tvdesc = (TextView) row.findViewById(R.id.tvdesc);
            TextView tvstts = (TextView) row.findViewById(R.id.tvstts);
            tvctype.setText(arrayList.get(i).getCrime_type());
            tvlocation.setText(arrayList.get(i).getLocation());
            tvdesc.setText(arrayList.get(i).getDescription());
            tvstts.setText(arrayList.get(i).getStatus());
            return row;
        }
    }
    private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(CrimeList.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                finish();
                Intent i = new Intent(CrimeList.this, Login.class);
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