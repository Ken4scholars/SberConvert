package ru.sberbank.sberconvert.presenter;

import java.util.List;

import ru.sberbank.sberconvert.model.Currency;

/**
 * Created by kenneth on 25.05.17.
 */

public interface CurrencyModelCallBack {

    void onFetchSuccess(List<Currency> currencies);
    void onAPIFailure();

    void onCacheFailure();
}
