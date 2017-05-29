package ru.sberbank.sberconvert.parser;

import org.simpleframework.xml.transform.Transform;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by kenneth on 28.05.17.
 */

public class CommaDoubleTransformer implements Transform<Double> {
    private DecimalFormat decimalFormat;
    public CommaDoubleTransformer(){
        decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(symbols);
    }

    @Override
    public Double read(String value) throws Exception {
        return decimalFormat.parse(value).doubleValue();
    }

    @Override
    public String write(Double value) throws Exception {
        return decimalFormat.format(value);
    }
}
