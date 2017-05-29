package ru.sberbank.sberconvert.view;

import java.util.List;

import ru.sberbank.sberconvert.model.Currency;

/**
 * Created by kenneth on 25.05.17.
 */

public interface CurrencyViewOps {

    void fillRates(List<Currency> currencies, int fromPos, int toPos);
    void showError(String errorMessage);
}
