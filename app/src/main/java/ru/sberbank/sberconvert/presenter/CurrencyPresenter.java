package ru.sberbank.sberconvert.presenter;

import android.content.Context;
import android.content.res.Resources;

import java.util.List;

import ru.sberbank.sberconvert.AppFactory;
import ru.sberbank.sberconvert.R;
import ru.sberbank.sberconvert.model.Currency;
import ru.sberbank.sberconvert.model.CurrencyModelOps;
import ru.sberbank.sberconvert.view.CurrencyViewOps;

/**
 * Created by kenneth on 25.05.17.
 */

public class CurrencyPresenter implements CurrencyPresenterToView{

    private CurrencyModelOps model;
    private CurrencyViewOps view;
    private Resources res;
    private CallBackImpl callBack;
    private List<Currency> currencies;

    public CurrencyPresenter(CurrencyViewOps view, Context context){
        this.view = view;
        this.model = AppFactory.getNewCurrencyModel(context.getCacheDir().getPath());
        res = context.getResources();
        callBack = new CallBackImpl();
    }

    private void onFetchSuccessReceiver(List<Currency> currencies) {
        this.currencies = currencies;
        int dollarPos = 0;
        for(int i = 0; i < currencies.size(); i++){
            if (currencies.get(i).getCharCode().equals("USD")){
                dollarPos = i;
                break;
            }
        }
        view.fillRates(currencies, 0, dollarPos);
    }

    private void onAPIFailureReceiver() {
        String errorMessage = res.getString(R.string.fetch_error);
        view.showError(errorMessage);
        model.getRatesFromCache(callBack);
    }

    public void onCacheFailureReceiver() {
        String errorMessage = res.getString(R.string.fetch_error);
        view.showError(errorMessage);
    }

    @Override
    public void fetchRates() {
      model.getRatesFromAPI(callBack);
    }

    @Override
    public double convert(String from, String to, double amount) {
        if(currencies != null){
            Currency fromCurrency = null;
            Currency toCurrency = null;
            for(Currency currency: currencies){
                if(currency.getCharCode().equals(from)){
                    fromCurrency = currency;
                }
                if(currency.getCharCode().equals(to)){
                    toCurrency = currency;
                }
                if(fromCurrency != null && toCurrency != null){
                    break;
                }
            }
            return Math.round(model.convert(fromCurrency, toCurrency, amount) * 10000.0) / 10000.0;
        }
        view.showError(res.getString(R.string.not_loaded_error));
        return 0;
    }

    private class CallBackImpl implements CurrencyModelCallBack{

        @Override
        public void onFetchSuccess(List<Currency> currencies) {
            onFetchSuccessReceiver(currencies);
        }

        @Override
        public void onAPIFailure() {
            onAPIFailureReceiver();
        }

        @Override
        public void onCacheFailure() {
            onCacheFailureReceiver();
        }
    }
}
