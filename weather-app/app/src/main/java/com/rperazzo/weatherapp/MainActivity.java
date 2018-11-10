package com.rperazzo.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.rperazzo.weatherapp.WeatherManager.FindResult;
import com.rperazzo.weatherapp.adapter.FindItemAdapter;
import com.rperazzo.weatherapp.controller.WeatherController;
import com.rperazzo.weatherapp.listener.IView;
import com.rperazzo.weatherapp.model.City;
import com.rperazzo.weatherapp.provider.IWeatherProvider;
import com.rperazzo.weatherapp.service.CityWeatherService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IView {


    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<City> cities = new ArrayList<>();
    private WeatherController  weatherController;
    private CityWeatherService service;

    private SharedPreferences mSharedPref;
    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";

    private static final String PREFERENCE_NAME = "com.rperazzo.weatherapp.shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSharedPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);


        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mList = (ListView) findViewById(R.id.list);

        service = new CityWeatherService();
        weatherController = new WeatherController(this, service);


        mAdapter = new FindItemAdapter(this, cities, getTemperatureUnit());
        mList.setAdapter(mAdapter);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchByName();
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
        String currentUnits = getTemperatureUnit();
        if (!currentUnits.equals(newUnits)) {
            setTemperatureUnit(newUnits);
            searchByName();
        }
    }

    public void onSearchClick(View view) {
        searchByName();
    }

    private void onStartLoading() {
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

    private void onFinishLoading(WeatherManager.FindResult result) {

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

    private void onFinishLoadingWithError() {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setText("Error");
    }

    public boolean isDeviceConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void searchByName() {

        if (!isDeviceConnected()) {
            Toast.makeText(this, "No connection!", Toast.LENGTH_LONG).show();
            return;
        }

        onStartLoading();

        weatherController.searchByName(mEditText.getText().toString(), getTemperatureUnit());

    }

    @Override
    public void update() {

        List<City> cidades = service.getCities();
        mAdapter = new FindItemAdapter(this, cidades , getTemperatureUnit());
        mList.setAdapter(mAdapter);

        mProgressBar.setVisibility(View.GONE);
        cities.clear();

        if (cidades.size() > 0) {
            mList.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mTextView.setText("No results.");
        }
    }

    public String getTemperatureUnit() {
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }

    public void setTemperatureUnit(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }
}
