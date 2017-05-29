package ru.sberbank.sberconvert.view;

import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map.Entry;

import ru.sberbank.sberconvert.AppFactory;
import ru.sberbank.sberconvert.R;
import ru.sberbank.sberconvert.model.Currency;
import ru.sberbank.sberconvert.presenter.CurrencyPresenterToView;

public class ConverterActivity extends AppCompatActivity implements CurrencyViewOps, AdapterView.OnItemSelectedListener {


    private Spinner fromSpinner;
    private Spinner toSpinner;
    private CurrencyPresenterToView presenter;
    private String fromCurrency;
    private String toCurrency;
    private EditText fromAmount;
    private TextView toAmount;
    private static final String TAG = "ConverterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        fromSpinner.setOnItemSelectedListener(this);
        toSpinner = (Spinner) findViewById(R.id.to_spinner);
        toSpinner.setOnItemSelectedListener(this);

        fromAmount = (EditText) findViewById(R.id.from_amount);
        toAmount = (TextView) findViewById(R.id.to_amount);
        fromCurrency = "";
        toCurrency = "";


//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        toolbar.setTitle(getResources().getString(R.string.app_name));
//        setSupportActionBar(toolbar);

        presenter = AppFactory.getNewCurrencyPresenter(this, getApplicationContext());
        presenter.fetchRates();


    }

    @Override
    public void fillRates(List<Currency> currencies, int fromPos, int toPos) {

        fromCurrency = currencies.get(fromPos).getCharCode();
        toCurrency = currencies.get(toPos).getCharCode();
        ArrayAdapter spinnerAdapter = AppFactory.getArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item, currencies);
        fromSpinner.setAdapter(spinnerAdapter);
        toSpinner.setAdapter(spinnerAdapter);
        fromSpinner.setSelection(fromPos);
        toSpinner.setSelection(toPos);
//        toAmount.setText("0");
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void convert(View view){
        String amount = fromAmount.getText().toString().trim();
        if(amount.equals("") || fromCurrency.equals("") || toCurrency.equals("")){
            showError(getResources().getString(R.string.required_error));
        }else {
            Log.d(TAG, "FromCurrency: " + fromCurrency);
            Log.d(TAG, "ToCurrency: " + toCurrency);
            double converted = presenter.convert(fromCurrency, toCurrency, Double.parseDouble(amount));
            toAmount.setText(Double.toString(converted));
        }
    }

    public void reverse(View view){
        int temp = fromSpinner.getSelectedItemPosition();
        fromSpinner.setSelection(toSpinner.getSelectedItemPosition());
        toSpinner.setSelection(temp);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = ((Entry) parent.getItemAtPosition(position)).getKey().toString();
        if(parent.getId() == fromSpinner.getId()){
            fromCurrency = item;
        }else toCurrency = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
