package ru.sberbank.sberconvert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.sberconvert.model.Currency;
import ru.sberbank.sberconvert.model.CurrencyManager;
import ru.sberbank.sberconvert.presenter.CurrencyModelCallBack;

import static junit.framework.Assert.*;


/**
 * Created by kenneth on 29.05.17.
 */

public class CurrencyManagerTest {

    CurrencyManager model;
    CurrencyModelCallBack callBack;
    Currency ruble;
    Currency dollar;
    List<Currency> curs;

    @Before
    public void setup(){
        model = new CurrencyManager(AppFactory.getResourceParser(), "");
        callBack = new CurrencyModelCallBack() {
            @Override
            public void onFetchSuccess(List<Currency> currencies) {

            }

            @Override
            public void onAPIFailure() {

            }

            @Override
            public void onCacheFailure() {

            }
        };

        dollar = new Currency();
        dollar.setID("R01235");
        dollar.setNumCode(840);
        dollar.setCharCode("USD");
        dollar.setName("Доллар США");
        dollar.setNominal(1);
        dollar.setValue(56.7106);

        ruble = new Currency();
        ruble.setID("R00000");
        ruble.setNumCode(643);
        ruble.setCharCode("RUB");
        ruble.setName("Российский рубль");
        ruble.setNominal(1);
        ruble.setValue(1.0);

        curs = new ArrayList<>();
        curs.add(ruble);
        curs.add(dollar);
    }

    @Test
    public void testConvert(){
        assertEquals(1.0, model.convert(ruble, dollar, 56.7106), 0.0001);
    }
}
