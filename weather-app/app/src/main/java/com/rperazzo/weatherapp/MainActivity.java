package com.rperazzo.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rperazzo.weatherapp.Model.City;
import com.rperazzo.weatherapp.Model.CityRepository;
import com.rperazzo.weatherapp.Model.ICityRepository;
import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.Service.WeatherManager.FindResult;
import com.rperazzo.weatherapp.Service.WeatherService;
import com.rperazzo.weatherapp.Storage.TemperatureSharedPref;
import com.rperazzo.weatherapp.Util.ConnectivityUtil;
import com.rperazzo.weatherapp.View.FindItemAdapter;
import com.rperazzo.weatherapp.View.Interface.ISearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ISearch {

    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<City> cities = new ArrayList<>();
    private TemperatureSharedPref temperatureSharedPref;
    private ICityRepository cityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityRepository = new CityRepository(this);

        temperatureSharedPref = new TemperatureSharedPref(getApplicationContext());

        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mList = (ListView) findViewById(R.id.list);

        mAdapter = new FindItemAdapter(this, cities);
        mList.setAdapter(mAdapter);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cityRepository.searchByName(mEditText.getText().toString(), MainActivity.this);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_celcius) {
            updateUnitIfNecessary("metric");
            return true;
        } else if (id == R.id.menu_fahrenheit) {
            updateUnitIfNecessary("imperial");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUnitIfNecessary(String newUnits) {
        String currentUnits = temperatureSharedPref.getTemperatureUnit();
        if (!currentUnits.equals(newUnits)) {
            temperatureSharedPref.setTemperatureUnit(newUnits);
            cityRepository.searchByName(mEditText.getText().toString(), this);
        }
    }

    public void onSearchClick(View view) {
        cityRepository.searchByName(mEditText.getText().toString(), this);
    }

    @Override
    public void onStartLoading() {
        mList.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                     getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onFinishLoading(WeatherManager.FindResult result){

        mProgressBar.setVisibility(View.GONE);
        cities.clear();

        if (result.list.size() > 0) {
            cities.addAll(result.list);
            mList.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mTextView.setText("No results.");
        }
    }

    @Override
    public void onFinishLoadingWithError() {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setText("Error");
    }

}
