package com.mycompany.googleimagesearch.dialogs;

/**
 * Created by ekucukog on 5/14/2015.
 */

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.mycompany.googleimagesearch.R;
import com.mycompany.googleimagesearch.models.Filter;

public class FilterDialog extends DialogFragment {

    private Filter m_filter;
    private Spinner spinnerFilterSize;
    private Spinner spinnerFilterColor;
    private Spinner spinnerFilterType;
    private EditText etFilterSite;
    private Button btSave;

    public FilterDialog() {
        // Empty constructor required for DialogFragment
    }

    public interface FilterDialogListener {
        void onFinishFilterDialog(Filter filter);
    }


    public static FilterDialog newInstance(Filter filter, String title) {
        FilterDialog frag = new FilterDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelable("filter", filter);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container);

        String title = getArguments().getString("title", getResources().getString(R.string.advanced_filters));
        getDialog().setTitle(title);
        //Show soft keyboard automatically
        //mEditText.requestFocus();
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //extract data
        m_filter = (Filter) getArguments().getParcelable("filter");

        setupViews(view);
        addListenerOnButton(view);
        addListenerOnSpinnerItemSelection(view);

        return view;
    }

    private void setupViews(View view){
        spinnerFilterSize = (Spinner) view.findViewById(R.id.spinnerFilterSize);
        spinnerFilterColor = (Spinner) view.findViewById(R.id.spinnerFilterColor);
        spinnerFilterType = (Spinner) view.findViewById(R.id.spinnerFilterType);
        etFilterSite = (EditText) view.findViewById(R.id.etFilterSite);

        etFilterSite.setText(m_filter.site);

        setSpinnerToValue(spinnerFilterSize, m_filter.size);
        setSpinnerToValue(spinnerFilterColor, m_filter.color);
        setSpinnerToValue(spinnerFilterType, m_filter.type);
    }

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

    public void addListenerOnButton(View view){
        Button btSave = (Button) view.findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Get the fields

                String size  = spinnerFilterSize.getSelectedItem().toString();
                String color  = spinnerFilterColor.getSelectedItem().toString();
                String type  = spinnerFilterType.getSelectedItem().toString();
                String site  = etFilterSite.getText().toString();

                m_filter = new Filter(size, color, type, site);

                FilterDialogListener listener = (FilterDialogListener) getActivity();
                listener.onFinishFilterDialog(m_filter);
                dismiss();
            }
        });
    }

    public void addListenerOnSpinnerItemSelection(View view) {
        spinnerFilterSize = (Spinner) view.findViewById(R.id.spinnerFilterSize);
        spinnerFilterSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i("DEBUG", "Spinner listener: " + parent.getItemAtPosition(position).toString());
                //Toast.makeText(parent.getContext(), "OnItemSelectedListener for Size : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterColor = (Spinner) view.findViewById(R.id.spinnerFilterColor);
        spinnerFilterColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i("DEBUG", "Spinner listener: " + parent.getItemAtPosition(position).toString());
                //Toast.makeText(parent.getContext(),"OnItemSelectedListener for Color : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterType = (Spinner) view.findViewById(R.id.spinnerFilterType);
        spinnerFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i("DEBUG", "Spinner listener: " + parent.getItemAtPosition(position).toString());
                //Toast.makeText(parent.getContext(),"OnItemSelectedListener for Type : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}