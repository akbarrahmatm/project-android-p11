package com.akbarrahmatm.a2112500851_akbarrahmatm_apicrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
    }

    public void setAdapter(ListAdapter adapter) {
    }

    public void setOnItemClickListener(TampilSemuaMahasiswa tampilSemuaMahasiswa) {
    }

    public interface OnitemClickListener {
        void setOnItemClickListener(AdapterView<?> parent, View view, int position, long id);
    }
}