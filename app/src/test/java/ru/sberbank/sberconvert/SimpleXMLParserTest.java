package ru.sberbank.sberconvert;



import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.sberbank.sberconvert.model.Currency;
import ru.sberbank.sberconvert.model.Rates;
import ru.sberbank.sberconvert.parser.CommaDoubleTransformer;
import ru.sberbank.sberconvert.parser.ParserException;
import ru.sberbank.sberconvert.parser.SimpleDateTransformer;
import ru.sberbank.sberconvert.parser.SimpleXMLParser;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kenneth on 29.05.17.
 */

public class SimpleXMLParserTest {

    private String xmlString = "<ValCurs Date=\"30.05.2017\" name=\"Foreign Currency Market\">" +
                                    "<Valute ID=\"R01010\">" +
                                        "<NumCode>036</NumCode>" +
                                        "<CharCode>AUD</CharCode>" +
                                        "<Nominal>1</Nominal>" +
                                        "<Name>Австралийский доллар</Name>" +
                                        "<Value>42,1870</Value>" +
                                    "</Valute>" +
                                    "<Valute ID=\"R01020A\">" +
                                        "<NumCode>944</NumCode>" +
                                        "<CharCode>AZN</CharCode>" +
                                        "<Nominal>1</Nominal>" +
                                        "<Name>Азербайджанский манат</Name>" +
                                        "<Value>33,3200</Value>" +
                                    "</Valute>" +
                                "</ValCurs>";
    private SimpleXMLParser parser;

    @Before
    public void setUp(){
        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Double.class, new CommaDoubleTransformer());
        matcher.bind(Date.class, new SimpleDateTransformer());
        parser = new SimpleXMLParser(matcher);
    }

    @Test
    public void testParseFromString() throws Exception{
        Rates rates = parser.parseFromString(xmlString);
        System.out.println(rates.getDate());
        assertEquals("AUD", rates.getCurrencies().get(0).getCharCode());
    }

    @Test(expected = ParserException.class)
//    @Test
    public void testIncorrectXML() throws Exception{
        parser.parseFromString(xmlString.substring(0, xmlString.length()-1));
    }

    @Test
    public void testFileParsing() throws Exception{
        Rates rates = new Rates();
        rates.setDate(Calendar.getInstance().getTime());
        rates.setName("test");

        Currency ruble = new Currency();
        ruble.setID("R00000");
        ruble.setNumCode(643);
        ruble.setCharCode("RUB");
        ruble.setName("Российский рубль");
        ruble.setNominal(1);
        ruble.setValue(1.0);
        List<Currency> curs = new ArrayList<>();
        curs.add(ruble);

        rates.setCurrencies(curs);

        //парсим в xml файл
        parser.serializeToFile(rates, "test.xml");

        //читаем из того же файла
        Rates parsedRates = parser.parseFromFile("test.xml");
        assertEquals("Российский рубль", parsedRates.getCurrencies().get(0).getName());
    }
}
