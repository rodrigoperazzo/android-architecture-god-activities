package com.rperazzo.weatherapp.presentation;

import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class WeatherPresenter implements WeatherContract.Presenter {

    WeatherRepository mWeatherRepository;
    SettingsRepository mSettingsRepository;

    private BehaviorSubject<Boolean> loadingSubject = BehaviorSubject.create();
    private BehaviorSubject<String> errorSubject = BehaviorSubject.create();
    private BehaviorSubject<List<City>> cityListSubject = BehaviorSubject.create();
    private BehaviorSubject<String> temperatureUnitSubject = BehaviorSubject.create();
    private Observable<List<City>> cityListObservable = cityListSubject;
    private Observable<Boolean> loadingObservable = loadingSubject;
    private Observable<String> errorObservable = errorSubject;
    private Observable<String> temperatureUnitObservable = temperatureUnitSubject;

    public WeatherPresenter(WeatherRepository weatherRepo, SettingsRepository settingsRepo) {
        mWeatherRepository = weatherRepo;
        mSettingsRepository = settingsRepo;
    }

    @Override
    public void onSearchClick(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return;
        }
        setLoading(true);

        String units = mSettingsRepository.getTemperatureUnit();
        mWeatherRepository.search(searchText, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<City>>(){
                    @Override
                    public void onSuccess(List<City> result) {
                        // update UI
                        onFinishSearching(result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        // show error
                        onFinishSearchingWithError(e.getMessage());
                    }
                });

    }

    @Override
    public void onUnitClick(String unitClicked, String currentText) {
        String currentUnits = mSettingsRepository.getTemperatureUnit();
        if (!currentUnits.equals(unitClicked)) {
            setTemperatureUnit(unitClicked);
            onSearchClick(currentText);
        }
    }

    @Override
    public void onFinishSearching(List<City> list) {
        if (list != null && list.size() > 0) {
            setCityList(list);
        } else {
            setError("No results.");
        }
        setLoading(false);
    }

    @Override
    public void onFinishSearchingWithError(String error) {
        setError(error);
    }

    private void setLoading(boolean bLoading){
        this.loadingSubject.onNext(bLoading);
    }

    private void setCityList(List<City> cidades){
        this.cityListSubject.onNext(cidades);

    }

    private void setError(String error){
        this.errorSubject.onNext(error);
    }

    private void setTemperatureUnit(String temperatureUnit){
        mSettingsRepository.setTemperatureUnit(temperatureUnit);
        this.temperatureUnitSubject.onNext(temperatureUnit);
    }

    public Observable<List<City>> getCityListObservable() {
        return cityListObservable;
    }

    public Observable<Boolean> getLoadingObservable() {
        return loadingObservable;
    }

    public Observable<String> getErrorObservable() {
        return errorObservable;
    }

    public Observable<String> getTemperatureUnitObservable() {
        return temperatureUnitObservable;
    }

}
