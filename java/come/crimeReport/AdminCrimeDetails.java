package come.crimeReport;

import android.content.Intent;
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

import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class AdminCrimeDetails extends AppCompatActivity {
    ListView lv;
    ArrayList<CrimeSetget> arrayList=new ArrayList<>();
    ImageView back,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crime_details);
        lv= (ListView) findViewById(R.id.lv);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        String url="http://103.230.103.142:8080/ejob/student/CARSystem/admin.php?crime_details";
        crimedetails(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(AdminCrimeDetails.this, AdminHome.class);
                startActivity(i);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(AdminCrimeDetails.this, Login.class);
                startActivity(i);

            }
        });
    }

    public void crimedetails(String url){
        new GetDataParser(AdminCrimeDetails.this, url, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                Log.i("urlsinup", Constantdat.registration);
                Log.i("response", String.valueOf(response));
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {


                            JSONArray jsonArray=response.getJSONArray("users");
                            for (int i=0;i<jsonArray.length();i++){
                                CrimeSetget crimeSetget=new CrimeSetget();
                                JSONObject jobj=jsonArray.getJSONObject(i);

                                crimeSetget.setId(jobj.getString("id"));
                                crimeSetget.setCrime_type(jobj.getString("crime_type"));
                                crimeSetget.setLocation(jobj.getString("location"));
                                crimeSetget.setDescription(jobj.getString("description"));
                                crimeSetget.setStatus(jobj.getString("status"));
                                arrayList.add(crimeSetget);
                            }
                            Toast.makeText(AdminCrimeDetails.this, status, Toast.LENGTH_LONG).show();
                            lv.setAdapter(new MyAdap());

                        } else {
                            Toast.makeText(AdminCrimeDetails.this, status, Toast.LENGTH_LONG).show();

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
            LayoutInflater inf=getLayoutInflater();
            View row=inf.inflate(R.layout.crimecontent,null);
            TextView tvctype=(TextView)row.findViewById(R.id.tvctype);
            TextView tvlocation=(TextView)row.findViewById(R.id.tvlocation);
            TextView tvdesc=(TextView)row.findViewById(R.id.tvdesc);
            TextView tvstts=(TextView)row.findViewById(R.id.tvstts);
            tvctype.setText(arrayList.get(i).getCrime_type());
            tvlocation.setText(arrayList.get(i).getLocation());
            tvdesc.setText(arrayList.get(i).getDescription());
            tvstts.setText(arrayList.get(i).getStatus());
            return row;
        }
    }
}
