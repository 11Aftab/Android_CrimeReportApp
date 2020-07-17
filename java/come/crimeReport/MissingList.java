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

public class MissingList extends AppCompatActivity {

    ListView lv;
    ArrayList<MissingSetget> arrayList = new ArrayList<>();
    ImageView back, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_list);
        lv = (ListView) findViewById(R.id.lv);
        back = (ImageView) findViewById(R.id.back);
        logout = (ImageView) findViewById(R.id.logout);
        String url = Constantdat.sgetMissingDetailsList+"user_id="+ AppData.userId;
        userdetails(url);

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

    public void userdetails(String url) {
        new GetDataParser(MissingList.this, url, true, new GetDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                String success, status, user_id;
                if (response != null) {
                    try {
                        success = response.getString("success");
                        status = response.getString("status_msg");
                        if (success.equals("1")) {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MissingSetget missingSetget = new MissingSetget();
                                JSONObject jobj = jsonArray.getJSONObject(i);

                                missingSetget.setId(jobj.getString("id"));
                                missingSetget.setName(jobj.getString("name"));
                                missingSetget.setAge(jobj.getString("age"));
                                missingSetget.setGender(jobj.getString("gender"));
                                missingSetget.setHeight(jobj.getString("height"));
                                missingSetget.setLastseen(jobj.getString("lastseen"));
                                missingSetget.setDescription(jobj.getString("description"));
                                missingSetget.setAddress(jobj.getString("address"));
                                missingSetget.setPhone(jobj.getString("phone"));
                                missingSetget.setStatus(jobj.getString("status"));
                                arrayList.add(missingSetget);
                            }
                            lv.setAdapter(new MyAdap());

                        } else {
                            Toast.makeText(MissingList.this, status, Toast.LENGTH_LONG).show();

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
            View row = inf.inflate(R.layout.missingcontent, null);
            TextView tvname = (TextView) row.findViewById(R.id.tvname);
            TextView tvage = (TextView) row.findViewById(R.id.tvage);
            TextView tvgender = (TextView) row.findViewById(R.id.tvgender);
            TextView tvhight = (TextView) row.findViewById(R.id.tvhight);
            TextView tvlastseen = (TextView) row.findViewById(R.id.tvlastseen);
            TextView tvdes = (TextView) row.findViewById(R.id.tvdes);
            TextView tvadd = (TextView) row.findViewById(R.id.tvadd);
            TextView tvgph = (TextView) row.findViewById(R.id.tvgph);
            TextView tvstus = (TextView) row.findViewById(R.id.tvstus);
            tvname.setText(arrayList.get(i).getName());
            tvage.setText(arrayList.get(i).getAge());
            tvgender.setText(arrayList.get(i).getGender());
            tvhight.setText(arrayList.get(i).getHeight());
            tvlastseen.setText(arrayList.get(i).getLastseen());
            tvdes.setText(arrayList.get(i).getDescription());
            tvadd.setText(arrayList.get(i).getAddress());
            tvgph.setText(arrayList.get(i).getPhone());
            tvstus.setText(arrayList.get(i).getStatus());
            return row;
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }  private  void showdialog(){
        final AlertDialog.Builder obj=new AlertDialog.Builder(MissingList.this);
        obj.setTitle("Logout ? ");
        obj.setTitle("Do You Want To Logout ");
        obj.setCancelable(false);
        obj.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                obj.setCancelable(true);
                finish();
                Intent i = new Intent(MissingList.this, Login.class);
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
