package ru.sberbank.sberconvert.presenter;


/**
 * Created by kenneth on 25.05.17.
 */

public interface CurrencyPresenterToView {

    void fetchRates();
    double convert(String from, String to, double amount);
}
