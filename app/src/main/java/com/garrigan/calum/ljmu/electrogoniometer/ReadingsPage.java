package com.garrigan.calum.ljmu.electrogoniometer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReadingsPage extends AppCompatActivity {
    private RecyclerView patientList;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_readings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patientList = findViewById(R.id.patientList);
        patientList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ListAdapter(MainPage.data);
        patientList.setLayoutManager(layoutManager);
        patientList.setAdapter(adapter);
        patientList.addItemDecoration(new DividerItemDecoration(patientList.getContext(), DividerItemDecoration.VERTICAL));

        TextView bntSend = findViewById(R.id.send_button);
        if(bntSend != null) {
            bntSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Electrogoniometer Readings");
                    intent.putExtra(Intent.EXTRA_TEXT, MainPage.data.toString());
                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
            });
        }

        TextView btnClear = findViewById(R.id.clear_button);
        if(btnClear != null) {
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainPage.data.clear();
                    patientList.getAdapter().notifyDataSetChanged();
                    Set<String> set = new HashSet<String>();
                    set.addAll(MainPage.data);
                    getSharedPreferences("app", MODE_PRIVATE).edit().putStringSet("measurements", set).commit();
                }
            });
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Snackbar toast = Snackbar.make(patientList, "Long press to remove item", Snackbar.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ExampleViewHolder> {
        private  ArrayList<String> data;

        public  class ExampleViewHolder extends RecyclerView.ViewHolder {
            public TextView measureItem;

            public ExampleViewHolder(View itemView) {
                super(itemView);
                measureItem = itemView.findViewById(R.id.measure_item);
            }
        }

        public ListAdapter(ArrayList<String> data){
            this.data  = data;
        }

        @NonNull
        @Override
        public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_detail, parent, false);
            return new ExampleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
            holder.measureItem.setText(data.get(position));
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            data.remove(position);
                            adapter.data = MainPage.data;
                            Set<String> set = new HashSet<String>();
                            set.addAll(MainPage.data);
                            getSharedPreferences("app", MODE_PRIVATE).edit().putStringSet("measurements", set).commit();

                            adapter.notifyDataSetChanged();
                        }
                    });

                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
