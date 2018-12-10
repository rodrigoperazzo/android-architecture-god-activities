package com.rperazzo.weatherapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.rperazzo.weatherapp.R;
import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.settings.SettingsRepositoryImpl;
import com.rperazzo.weatherapp.model.settings.local.SettingsLocal;
import com.rperazzo.weatherapp.model.settings.local.SettingsLocalImpl;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;
import com.rperazzo.weatherapp.model.weather.WeatherRepositoryImpl;
import com.rperazzo.weatherapp.model.weather.remote.WeatherRemote;
import com.rperazzo.weatherapp.model.weather.remote.WeatherRemoteImpl;
import com.rperazzo.weatherapp.presentation.WeatherContract;
import com.rperazzo.weatherapp.presentation.WeatherPresenter;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherContract.View {

    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<City> cities = new ArrayList<>();

    WeatherContract.Presenter mPresenter;

    private Disposable errorObserver;
    private Disposable cityListObservable;
    private Disposable temperatureUnitObserver;
    private Disposable loadingObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);
        mProgressBar = findViewById(R.id.progressBar);
        mList = findViewById(R.id.list);

        mAdapter = new FindItemAdapter(this, cities);
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
        initPresenter();
    }

    private void initPresenter() {
        WeatherRemote weatherRemote = new WeatherRemoteImpl();
        WeatherRepository weatherRepository = new WeatherRepositoryImpl(weatherRemote);
        SettingsLocal settingsLocal = new SettingsLocalImpl(this);
        SettingsRepository settingsRepository = new SettingsRepositoryImpl(settingsLocal);
        mPresenter = new WeatherPresenter(weatherRepository, settingsRepository);
    }

    @Override
    protected void onStart() {
        super.onStart();
        errorObserver = mPresenter.getErrorObservable().doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                onFinishLoadingWithError(s);
            }
        }).subscribe();

        cityListObservable = mPresenter.getCityListObservable().doOnNext(new Consumer<List<City>>() {
            @Override
            public void accept(List<City> cities) throws Exception {
                onFinishLoading(cities);
            }
        }).subscribe();

        temperatureUnitObserver = mPresenter.getTemperatureUnitObservable().doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                onChangeUnit(s);
            }
        }).subscribe();

        loadingObservable = mPresenter.getLoadingObservable().doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    onStartLoading();

                }
                else{
                    onStopLoading();
                }
            }
        }).subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cityListObservable.dispose();
        errorObserver.dispose();
        loadingObservable.dispose();
        temperatureUnitObserver.dispose();
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
            onUnitClick("metric");
            return true;
        } else if (id == R.id.menu_fahrenheit) {
            onUnitClick("imperial");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onUnitClick(String unitClicked) {
        String search = mEditText.getText().toString();
        mPresenter.onUnitClick(unitClicked, search);
    }

    public void onSearchClick(View view) {
        searchByName();
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
    public void onFinishLoadingWithError(String error) {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(error);
    }

    public void onChangeUnit( String units) {
        mAdapter.setUnits(units);
    }

    public void onStopLoading() {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }

    public void onFinishLoading(List<City> list) {
        cities.clear();
        cities.addAll(list);
        mAdapter.notifyDataSetChanged();
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

        String search = mEditText.getText().toString();
        mPresenter.onSearchClick(search);
    }

}
