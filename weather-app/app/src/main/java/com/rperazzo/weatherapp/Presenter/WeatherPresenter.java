package com.rperazzo.weatherapp.Presenter;

import com.rperazzo.weatherapp.Model.CityRepository;
import com.rperazzo.weatherapp.Model.ICityRepository;
import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.Util.IConnectivityUtil;
import com.rperazzo.weatherapp.View.Interface.ISearch;

/**
 * Created by Thiago on 10/11/2018.
 */

public class WeatherPresenter implements IWeatherPresenter {

    private ICityRepository cityRepository;
    private ISearch search;

    public WeatherPresenter(ISearch search){
        this.search = search;
        cityRepository = new CityRepository();
    }

    @Override
    public boolean searchByName(String name, String units, String lang, IConnectivityUtil connect) {

        search.onStartLoading();
        return cityRepository.searchByName(name, this, units, lang, connect);
    }

    @Override
    public void onResult(WeatherManager.FindResult result) {
        search.onFinishLoading(result);
    }

    @Override
    public void onResultWithError(String message) {
        search.onFinishLoadingWithError(message);
    }
}
