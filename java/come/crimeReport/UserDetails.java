package come.crimeReport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import come.crimeReport.app.AppController;
import come.crimeReport.app.AppData;
import come.crimeReport.parser.GetDataParser;
import come.crimeReport.url.Constantdat;

public class UserDetails extends AppCompatActivity {
    ListView lv;
    ArrayList<Userseget>arrayList=new ArrayList<>();
    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        lv= (ListView) findViewById(R.id.lv);
        back=(ImageView) findViewById(R.id.back);
        logout=(ImageView) findViewById(R.id.logout);
        String url="http://103.230.103.142:8080/ejob/student/CARSystem/admin.php?user_details";
        userdetails(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(UserDetails.this, AdminHome.class);
                startActivity(i);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(UserDetails.this, Login.class);
                startActivity(i);

            }
        });

    }

    public void data(final String url){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("urlsinup",url);
                Log.d("response", String.valueOf(jsonObject));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

 public void userdetails(final String url){
         new GetDataParser(UserDetails.this, url, true, new GetDataParser.OnGetResponseListner() {
             @Override
             public void onGetResponse(JSONObject response) {
                 String success, status;
                 Log.i("urlsinup",url);
                 Log.i("response", String.valueOf(response));
                 if (response != null) {
                     try {
                         success = response.getString("success");
                         status = response.getString("status_msg");
                         if (success.equals("1")) {


                             JSONArray jsonArray=response.getJSONArray("users");
                             for (int i=0;i<jsonArray.length();i++){
                                 Userseget userseget=new Userseget();
                                JSONObject jobj=jsonArray.getJSONObject(i);

                                 userseget.setId(jobj.getString("id"));
                                 userseget.setName(jobj.getString("name"));
                                 userseget.setEmail(jobj.getString("email"));
                                 userseget.setGender(jobj.getString("gender"));
                                 userseget.setPhno(jobj.getString("phone"));
                                 userseget.setAddress(jobj.getString("address"));
                                 arrayList.add(userseget);
                             }
                             Toast.makeText(UserDetails.this, status, Toast.LENGTH_LONG).show();
                             lv.setAdapter(new MyAdap());

                         } else {
                             Toast.makeText(UserDetails.this, status, Toast.LENGTH_LONG).show();

                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }

             }
         });

 }

 //public void
     class MyAdap extends BaseAdapter{

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
             View row=inf.inflate(R.layout.usercontent,null);
             TextView tvname=(TextView)row.findViewById(R.id.tvname);
             TextView tvemqail=(TextView)row.findViewById(R.id.tvemqail);
             TextView tvgender=(TextView)row.findViewById(R.id.tvgender);
             TextView tvgph=(TextView)row.findViewById(R.id.tvgph);
             TextView tvadd=(TextView)row.findViewById(R.id.tvadd);
             tvname.setText(arrayList.get(i).getName());
             tvemqail.setText(arrayList.get(i).getEmail());
             tvgender.setText(arrayList.get(i).getGender());
             tvgph.setText(arrayList.get(i).getPhno());
             tvadd.setText(arrayList.get(i).getAddress());
             return row;
         }
     }
    @Override
    public void onBackPressed() {
        finish();
        Intent i= new Intent(UserDetails.this,AdminHome.class);
        startActivity(i);

    }

}
