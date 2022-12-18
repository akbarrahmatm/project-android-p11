package com.akbarrahmatm.a2112500851_akbarrahmatm_apicrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilSemuaMahasiswa extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listview;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_mahasiswa);
        listview = findViewById(R.id.listView);
        listview.setOnItemClickListener(this);
        getJSON();

    }

    private void showMahasiswa() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Configuration.TAG_ID);
                String name = jo.getString(Configuration.TAG_NAMA);
                HashMap<String, String> employees = new HashMap<>();
                employees.put(Configuration.TAG_ID, id);
                employees.put(Configuration.TAG_NAMA, name);
                list.add(employees);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaMahasiswa.this, list, R.layout.list_item,
                new String[]{Configuration.TAG_ID, Configuration.TAG_NAMA},
                new int[]{R.id.id, R.id.nama});

        listview.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaMahasiswa.this, "Mengambil data", "Mohon Tunggu", false, false);
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMahasiswa();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequest(Configuration.URL_GET_ALL);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void setOnItemClickListener(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Intent intent = new Intent(this, TampilMahasiswa.class);

        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String mhsid = map.get(Configuration.TAG_ID).toString();
        intent.putExtra(Configuration.TAG_ID, mhsid);
        startActivity(intent);
    }


}