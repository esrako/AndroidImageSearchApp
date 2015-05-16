package com.mycompany.googleimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.*;
import com.mycompany.googleimagesearch.adapters.ImageResultsAdapter;
import com.mycompany.googleimagesearch.dialogs.FilterDialog;
import com.mycompany.googleimagesearch.listeners.EndlessScrollListener;
import com.mycompany.googleimagesearch.models.ImageResult;
import com.mycompany.googleimagesearch.models.Filter;
import com.mycompany.googleimagesearch.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity implements FilterDialog.FilterDialogListener {
    private StaggeredGridView sgvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    final int REQUEST_CODE = 10;
    String m_size;
    private Filter m_filter;
    private String m_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        sgvResults.setAdapter(aImageResults);
        m_filter = new Filter();
    }

    private void setupViews(){
        //etQuery = (EditText) findViewById(R.id.etQuery);
        sgvResults = (StaggeredGridView) findViewById(R.id.sgvResults);
        sgvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult ir = imageResults.get(position);
                i.putExtra("result", ir);
                startActivity(i);
            }
        });
        // Attach the listener to the AdapterView onCreate
        sgvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int page) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        // pages coming as 2-8, what I need for my page structure is 1-7
        submitQuery(page-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                m_query = query;
                Log.i("DEBUG", "Submitted the following query: " + query);
                submitQuery(0);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //page[0-7], 0:new search, 1-7:scrolling down
    private void submitQuery(int page){
        int start = page * 8;

        if(page>7){//no more results - do nothing
            Log.i("DEBUG", "Page: " + page);
            Toast.makeText(getApplicationContext(), R.string.end_of_results, Toast.LENGTH_LONG).show();
            return;
        }else{//page 0 to 7, load (more) results
            Log.i("DEBUG", "Page: " + page);
            //Toast.makeText(getApplicationContext(), "Will load page" + page, Toast.LENGTH_LONG).show();
        }

        if(!isThereNetworkConnection()){
            //toast the error
            Log.i("DEBUG", "No internet");
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }
        else {
            //Toast.makeText(this, "Searching for: " + m_query, Toast.LENGTH_SHORT).show();
            AsyncHttpClient client = new AsyncHttpClient();
            //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
            String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + m_query + "&rsz=8";
            String searchUrlFiltersApplied = applyFilters(searchUrl, m_filter, start);
            if(page==0){//first page - new search : clear adapter
                imageResults.clear(); //clear only in case of new search
                aImageResults.notifyDataSetChanged();
            }
            client.get(searchUrlFiltersApplied, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    Log.i("DEBUG", response.toString());
                    JSONArray imageResultJson = null;
                    try {
                        imageResultJson = response.getJSONObject("responseData").getJSONArray("results");

                        imageResults.addAll(ImageResult.fromJSONArray(imageResultJson));
                        aImageResults.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("DEBUG", imageResults.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", responseString);
                    Toast.makeText(getApplicationContext(), R.string.connection_failure, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("DEBUG", "Clicked to Settings");
            //Toast.makeText(this, "Clicked to settings!", Toast.LENGTH_SHORT).show();
            /*Intent i = new Intent(SearchActivity.this, SetFiltersActivity.class);
            i.putExtra("filter", m_filter);
            startActivityForResult(i, REQUEST_CODE);*/
            showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode== REQUEST_CODE && resultCode == RESULT_OK){
            m_filter = (Filter) data.getParcelableExtra("filter");
            Log.i("DEBUG", "onActivityResult: got the filter back");
            //Toast.makeText(this, m_filter.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String applyFilters(String url, Filter filter, int start){
        Log.i("DEBUG", url);
        StringBuilder sb = new StringBuilder(url);
        if(!filter.size.equals("") && !filter.size.equals("any")){
            sb.append("&imgsz=" + filter.size);
        }
        if(!filter.color.equals("") && !filter.color.equals("any")){
            sb.append("&imgcolor=" + filter.color);
        }
        if(!filter.type.equals("") && !filter.type.equals("any")){
            sb.append("&imgtype=" + filter.type);
        }
        if(!filter.site.equals("")){
            sb.append("&as_sitesearch=" + filter.site);
        }
        if(start>=0){
            sb.append("&start=" + start);
        }
        Log.i("DEBUG", sb.toString());
        return sb.toString();
    }

    private boolean isThereNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialog filterDialog = FilterDialog.newInstance(m_filter, getResources().getString(R.string.advanced_filters));
        filterDialog.show(fm, "fragment_filter");
    }

    @Override
    public void onFinishFilterDialog(Filter filter) {
        m_filter = filter;
        Log.i("DEBUG", "onFinishFilterDialog: got the filter back");
        //Toast.makeText(this, "Dialog returning filter: " + filter.toString(), Toast.LENGTH_SHORT).show();
    }
}
