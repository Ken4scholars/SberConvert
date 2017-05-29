package ru.sberbank.sberconvert;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import ru.sberbank.sberconvert.model.Currency;
import ru.sberbank.sberconvert.model.CurrencyManager;
import ru.sberbank.sberconvert.model.CurrencyModelOps;
import ru.sberbank.sberconvert.parser.CommaDoubleTransformer;
import ru.sberbank.sberconvert.parser.SimpleDateTransformer;
import ru.sberbank.sberconvert.parser.SimpleXMLParser;
import ru.sberbank.sberconvert.parser.ResourceParser;
import ru.sberbank.sberconvert.presenter.CurrencyPresenter;
import ru.sberbank.sberconvert.presenter.CurrencyModelCallBack;
import ru.sberbank.sberconvert.presenter.CurrencyPresenterToView;
import ru.sberbank.sberconvert.utils.TupleArrayAdapter;
import ru.sberbank.sberconvert.view.CurrencyViewOps;

/**
 * Created by kenneth on 27.05.17.
 */

public class AppFactory {

    public static CurrencyPresenterToView getNewCurrencyPresenter(CurrencyViewOps view, Context context){
        return new CurrencyPresenter(view, context);
    }

    public static CurrencyModelOps getNewCurrencyModel(String cachePath){
        return new CurrencyManager(getResourceParser(), cachePath);
    }

    public static ResourceParser getResourceParser(){
        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Double.class, new CommaDoubleTransformer());
        matcher.bind(Date.class, new SimpleDateTransformer());
        return new SimpleXMLParser(matcher);
    }

    public static ArrayAdapter<Entry> getArrayAdapter(Context context, int resourceId, List<Currency> currencies){
        List<Entry> data = new ArrayList<>();
        for(Currency currency: currencies){
            data.add(new SimpleEntry<>(currency.getCharCode(), currency.getName()));
        }
        return new TupleArrayAdapter(context, resourceId, data);
    }
}
