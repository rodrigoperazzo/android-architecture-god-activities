package com.rperazzo.weatherapp.listener;

import com.rperazzo.weatherapp.model.City;

import java.util.List;

public interface IView {

    void mostrarListaCidade(List<City> cities);
    void mostrarLoading();
    void removerLoading();
    void mostrarListaVazia();
    void mostrarSemInternet();

}
