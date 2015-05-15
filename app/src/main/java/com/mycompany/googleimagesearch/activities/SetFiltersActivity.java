package com.mycompany.googleimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.mycompany.googleimagesearch.R;
import com.mycompany.googleimagesearch.models.Filter;

public class SetFiltersActivity extends ActionBarActivity {

    private Filter m_filter;
    private Spinner spinnerFilterSize;
    private Spinner spinnerFilterColor;
    private Spinner spinnerFilterType;
    private EditText etFilterSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_filters);

        //extract data
        m_filter = (Filter) getIntent().getSerializableExtra("filter");

        setupViews();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    private void setupViews(){
        spinnerFilterSize = (Spinner)findViewById(R.id.spinnerFilterSize);
        spinnerFilterColor = (Spinner)findViewById(R.id.spinnerFilterColor);
        spinnerFilterType = (Spinner)findViewById(R.id.spinnerFilterType);
        etFilterSite = (EditText) findViewById(R.id.etFilterSite);

        etFilterSite.setText(m_filter.site);

        setSpinnerToValue(spinnerFilterSize, m_filter.size);
        setSpinnerToValue(spinnerFilterColor, m_filter.color);
        setSpinnerToValue(spinnerFilterType, m_filter.type);
    }

    //is this going to work?Yes
    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    public void addListenerOnButton(){
        Button btSave = (Button)findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Get the fields

                //is this correct?Yes
                String size  = spinnerFilterSize.getSelectedItem().toString();
                String color  = spinnerFilterColor.getSelectedItem().toString();
                String type  = spinnerFilterType.getSelectedItem().toString();
                String site  = etFilterSite.getText().toString();

                m_filter = new Filter(size, color, type, site);

                // 2. Build the response
                Intent result = new Intent();
                result.putExtra("filter", m_filter);
                setResult(RESULT_OK, result);
                // 3. Submit the form
                finish();
            }
        });
    }

    public void addListenerOnSpinnerItemSelection() {
        spinnerFilterSize = (Spinner) findViewById(R.id.spinnerFilterSize);
        spinnerFilterSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"OnItemSelectedListener for Size : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterColor = (Spinner) findViewById(R.id.spinnerFilterColor);
        spinnerFilterColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"OnItemSelectedListener for Color : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterType = (Spinner) findViewById(R.id.spinnerFilterType);
        spinnerFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"OnItemSelectedListener for Type : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
