package com.example.timestable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    static final int min_value=1;
    static final int max_value=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.tables);
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final EditText editText = findViewById(R.id.inputValueForTables);
        seekBar.setMax(30);
        seekBar.setProgress(10);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String inputString = charSequence.toString();
                int inputInt = 1;
                if (!inputString.equals("")) {
                    try {
                        inputInt = checkMin(Integer.parseInt(inputString));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                final int tableNum = checkMin(inputInt);
                generateTimesTable(tableNum);
                seekBar.setProgress(tableNum);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                editText.setText(String.valueOf(progress));
                int timesTable = checkMin(progress);
                seekBar.setProgress(timesTable);
                generateTimesTable(timesTable);
                Log.i("SeekBar Tables", String.valueOf(timesTable));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        generateTimesTable(10);
    }

    private int checkMin(int progress) {
        if (progress < min_value) {
            return 1;
        } else if (progress > max_value) {
            return max_value;
        }
        return progress;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void generateTimesTable(int timesTable){
        ArrayList<String> result = new ArrayList<>();
        for(int i=1;i<=10;i++){
            result.add(String.valueOf(i*timesTable));
        }

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, result);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}