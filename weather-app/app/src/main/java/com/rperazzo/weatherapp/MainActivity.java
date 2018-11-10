package com.rperazzo.weatherapp;

import android.content.Context;
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

import com.rperazzo.weatherapp.Presenter.MainActivityPresenter;
import com.rperazzo.weatherapp.View.*;

import com.rperazzo.weatherapp.Model.*;

import java.util.ArrayList;
import java.util.Comparator;

import com.rperazzo.weatherapp.Adapter.*;

public class MainActivity extends AppCompatActivity implements IView {

    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<City> cities = new ArrayList<>();
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

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
                    String search = mEditText.getText().toString();
                    presenter.searchByName(search);
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

        String search = mEditText.getText().toString();

        if (id == R.id.menu_celcius) {
            presenter.updateUnitIfNecessary("metric", search);
            return true;
        } else if (id == R.id.menu_fahrenheit) {
            presenter.updateUnitIfNecessary("imperial", search);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void showMessage(String message){
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();

    }

    public void onSearchClick(View view) {
        String search = mEditText.getText().toString();
        presenter.searchByName(search);
    }

    public Context getContext() {

        return this;
    }

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

    public void onFinishLoading(WeatherManager.FindResult result) {

        mProgressBar.setVisibility(View.GONE);
        cities.clear();

        if (result.list.size() > 0) {
            cities.addAll(result.list);
            cities.sort(new Comparator<City>() {
                @Override
                public int compare(City city, City t1) {
                    return city.getTemperature().compareTo(t1.getTemperature());
                }
            });
            mList.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mTextView.setText("No results.");
        }
    }

    public void onFinishLoadingWithError() {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setText("Error");
    }

//
//    private void searchByName() {
//        String search = mEditText.getText().toString();
//
//    }
}
