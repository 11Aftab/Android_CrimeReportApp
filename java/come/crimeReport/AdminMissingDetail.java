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

public class AdminMissingDetail extends AppCompatActivity {
    ListView lv;
    ArrayList<MissingSetget> arrayList=new ArrayList<>();
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_missing_detail);
        lv= (ListView) findViewById(R.id.lv);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        String url="http://103.230.103.142:8080/ejob/student/CARSystem/admin.php?missing_details";
        userdetails(url);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(AdminMissingDetail.this, AdminHome.class);
                startActivity(i);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(AdminMissingDetail.this, Login.class);
                startActivity(i);

            }
        });
    }

    public void userdetails(String url){
        new GetDataParser(AdminMissingDetail.this, url, true, new GetDataParser.OnGetResponseListner() {
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
                                MissingSetget missingSetget=new MissingSetget();
                                JSONObject jobj=jsonArray.getJSONObject(i);

                                missingSetget.setId(jobj.getString("id"));
                                missingSetget.setName(jobj.getString("name"));
                                missingSetget.setAge(jobj.getString("age"));
                                missingSetget.setGender(jobj.getString("gender"));
                                missingSetget.setHeight(jobj.getString("height"));
                                missingSetget.setHeight(jobj.getString("height"));
                                missingSetget.setLastseen(jobj.getString("lastseen"));
                                missingSetget.setDescription(jobj.getString("description"));
                                missingSetget.setAddress(jobj.getString("address"));
                                missingSetget.setPhone(jobj.getString("phone"));
                                missingSetget.setStatus(jobj.getString("status"));
                                arrayList.add(missingSetget);
                            }
                            Toast.makeText(AdminMissingDetail.this, status, Toast.LENGTH_LONG).show();
                            lv.setAdapter(new MyAdap());

                        } else {
                            Toast.makeText(AdminMissingDetail.this, status, Toast.LENGTH_LONG).show();

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
            View row=inf.inflate(R.layout.missingcontent,null);
            TextView tvname=(TextView)row.findViewById(R.id.tvname);
            TextView tvage=(TextView)row.findViewById(R.id.tvage);
            TextView tvgender=(TextView)row.findViewById(R.id.tvgender);
            TextView tvhight=(TextView)row.findViewById(R.id.tvhight);
            TextView tvlastseen=(TextView)row.findViewById(R.id.tvlastseen);
            TextView tvdes=(TextView)row.findViewById(R.id.tvdes);
            TextView tvadd=(TextView)row.findViewById(R.id.tvadd);
            TextView tvgph=(TextView)row.findViewById(R.id.tvgph);
            TextView tvstus=(TextView)row.findViewById(R.id.tvstus);
            tvname.setText(arrayList.get(i).getName());
            tvage.setText(arrayList.get(i).getAge());
            tvgender.setText(arrayList.get(i).getGender());
            tvhight.setText(arrayList.get(i).getHeight());
            tvlastseen.setText(arrayList.get(i).getLastseen());
            tvdes.setText(arrayList.get(i).getDescription());
            tvadd.setText(arrayList.get(i).getAddress());
            tvgph.setText(arrayList.get(i).getPhone());
            tvstus.setText(arrayList.get(i).getPhone());
            return row;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent i= new Intent(AdminMissingDetail.this,AdminHome.class);
        startActivity(i);

    }
}
