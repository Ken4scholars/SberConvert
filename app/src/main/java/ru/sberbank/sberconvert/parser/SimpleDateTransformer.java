package ru.sberbank.sberconvert.parser;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kenneth on 28.05.17.
 */

public class SimpleDateTransformer implements Transform<Date> {
    private DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    public Date read (String source) throws Exception {
        return sdf.parse (source);
    }
    public String write (Date source) throws Exception {
        return sdf.format(source);
    }
}
