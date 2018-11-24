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
import com.rperazzo.weatherapp.presentation.ViewModel;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.AsyncSubject;

public class MainActivity extends AppCompatActivity implements WeatherContract.View {

    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<City> cities = new ArrayList<>();

    ViewModel vModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        vModel = new ViewModel(weatherRepository, settingsRepository);
    }

    @Override
    protected void onStart() {
        super.onStart();
        vModel.onAttachView(this);
        bind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vModel.onDettachView();
        unbind();
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
        vModel.onUnitClick(unitClicked, search);
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

    public void onFinishLoading(List<City> list) {
        mProgressBar.setVisibility(View.GONE);
        cities.clear();
        cities.addAll(list);
        mList.setVisibility(View.VISIBLE);
//        mAdapter.setUnits(units);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishLoadingWithError(String error) {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setText(error);
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
        //mPresenter.onSearchClick(search);
        vModel.onSearchClick(search);
    }

    public void bind(){


        vModel.getLoadingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        b -> {
                            if (b){
                                onStartLoading();
                            }
                        },
                        error -> {
                            new Throwable("Erro");
                        });

        vModel.getCitySubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cities -> {
                            onFinishLoading(cities);
                        },
                        error -> {
                            new Throwable("Erro");
                        });

        vModel.getTempSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        c -> {
                            mAdapter.setUnits(c);
                        },
                        error -> {
                            new Throwable("Erro");
                        });


    }

    public void unbind(){

    }

}
