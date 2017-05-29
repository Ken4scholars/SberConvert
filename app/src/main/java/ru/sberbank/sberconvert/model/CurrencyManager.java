package ru.sberbank.sberconvert.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ru.sberbank.sberconvert.parser.ParserException;
import ru.sberbank.sberconvert.parser.ResourceParser;
import ru.sberbank.sberconvert.presenter.CurrencyModelCallBack;

import static ru.sberbank.sberconvert.utils.Constants.*;

/**
 * Created by kenneth on 25.05.17.
 */

public class CurrencyManager implements CurrencyModelOps {

    private String filePath;
    private List<Currency> currencies;
    private ResourceParser parser;

    private static final String TAG = "CurrencyManager";


    public CurrencyManager(ResourceParser parser, String cachePath){
        this.parser = parser;
        filePath = cachePath + CACHE_FILENAME;
        currencies = null;
    }

    @Override
    public void getRatesFromAPI(CurrencyModelCallBack callBack) {
        new DownloadRatesTask().execute(callBack);
    }

    @Override
    public void getRatesFromCache(CurrencyModelCallBack callBack) {
        try{
            currencies = parser.parseFromFile(filePath).getCurrencies();
            callBack.onFetchSuccess(currencies);
        }catch (ParserException e){
            callBack.onCacheFailure();
        }
    }

    private class DownloadRatesTask extends AsyncTask<CurrencyModelCallBack, Integer, List<Currency>>{

        private boolean error = false;
        private CurrencyModelCallBack callBack;

        @Override
        protected List<Currency> doInBackground(CurrencyModelCallBack... params) {
            try{

                callBack = params[0];

                URL url = new URL(DOWNLOAD_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                    Log.d(TAG, "Server error: " + connection.getResponseCode());
                    error = true;
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (connection.getInputStream(), "windows-1251"));
                StringBuilder buf = new StringBuilder(BUFFER_SIZE);
                String line;
                while((line = reader.readLine()) != null){
                    buf.append(line).append("\n");
                }
                String resourceString = new String(buf);
                reader.close();
                connection.disconnect();


                Rates rates = parser.parseFromString(resourceString);
                currencies = rates.getCurrencies();
                currencies.add(0, makeRuble());
                rates.setCurrencies(currencies);
                parser.serializeToFile(rates, filePath);

            }catch (IOException | ParserException e) {
                error = true;
            }
            return currencies;
        }

        @Override
        protected void onPostExecute(List<Currency> currencies) {
            if(error)callBack.onAPIFailure();
            else callBack.onFetchSuccess(currencies);
        }
    }

    @Override
    public double convert(Currency from, Currency to, double amount) {
        return amount * from.getRatio() / to.getRatio();
    }

    private Currency makeRuble(){
        Currency ruble = new Currency();
        ruble.setID("R00000");
        ruble.setNumCode(643);
        ruble.setCharCode("RUB");
        ruble.setName("Российский рубль");
        ruble.setNominal(1);
        ruble.setValue(1.0);
        return ruble;
    }

}
