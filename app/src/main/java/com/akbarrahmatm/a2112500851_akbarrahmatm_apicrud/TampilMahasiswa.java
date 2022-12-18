package com.akbarrahmatm.a2112500851_akbarrahmatm_apicrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilMahasiswa extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextAlamat;
    private EditText editTextJurusan;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_mahasiswa);
        Intent intent = getIntent();
        id = intent.getStringExtra(Configuration.TAG_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        editTextJurusan = (EditText) findViewById(R.id.editTextJurusan);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextId.setText(id);
        getMahasiswa();

    }

    private void getMahasiswa(){
        class GetMahasiswa extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilMahasiswa.this, "Fetching...","wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                showMahasiswa(s);
            }
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_MHS + id);
                System.err.println(s);
                return  s;
            }
        }
        GetMahasiswa ge = new GetMahasiswa();
        ge.execute();
    }
    private void showMahasiswa(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Configuration.TAG_NAMA);
            String desg = c.getString(Configuration.TAG_ALAMAT);
            String sal = c.getString(Configuration.TAG_JURUSAN);

            editTextNama.setText(name);
            editTextAlamat.setText(desg);
            editTextJurusan.setText(sal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateMahasiswa(){
        final String nama = editTextNama.getText().toString().trim();
        final String alamat = editTextAlamat.getText().toString().trim();
        final String jurusan = editTextJurusan.getText().toString().trim();

        class UpdateMahasiswa extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilMahasiswa.this, "Updating...", "Wait...", false, false);
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilMahasiswa.this,s,Toast.LENGTH_LONG).show();
                startActivity(new Intent(TampilMahasiswa.this, TampilSemuaMahasiswa.class));
            }
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Configuration.KEY_MHS_ID,id);
                hashMap.put(Configuration.KEY_MHS_NAMA,nama);
                hashMap.put(Configuration.KEY_MHS_ALAMAT,alamat);
                hashMap.put(Configuration.KEY_MHS_jurusan,jurusan);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Configuration.URL_UPDATE_MHS,hashMap);
                return s;
            }
        }
        UpdateMahasiswa ue = new UpdateMahasiswa();
        ue.execute();
    }
    private void deleteMahasiswa(){
        class deleteMahasiswa extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilMahasiswa.this, "Delete...", "wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilMahasiswa.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_DELETE_MHS + id);
                return s;
            }
        }
        deleteMahasiswa de = new deleteMahasiswa();
        de.execute();
    }
    private void confirmDeleteMahasiswa(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda Yakin Ingin Menghapus Mahasiswa ini?");

        alertDialogBuilder.setPositiveButton("ya", (dialogInterface, i) -> {
            deleteMahasiswa();
            startActivity(new Intent(TampilMahasiswa.this, TampilSemuaMahasiswa.class));
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View view) {
        if (view == buttonUpdate){
            updateMahasiswa();
        }
        if (view == buttonDelete){
            confirmDeleteMahasiswa();
        }
    }
}