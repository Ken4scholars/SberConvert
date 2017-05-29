package ru.sberbank.sberconvert.model;

import ru.sberbank.sberconvert.presenter.CurrencyModelCallBack;

/**
 * Created by kenneth on 25.05.17.
 */

public interface CurrencyModelOps {
    void getRatesFromAPI(CurrencyModelCallBack callBack);
    void getRatesFromCache(CurrencyModelCallBack callBack);
    double convert(Currency from, Currency to, double amount);
}
