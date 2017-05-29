package ru.sberbank.sberconvert.parser;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import ru.sberbank.sberconvert.model.Rates;

/**
 * Created by kenneth on 25.05.17.
 */

public class SimpleXMLParser implements ResourceParser {

    private static final String TAG = "SimpleXMLParser";
    private RegistryMatcher matcher;

    public SimpleXMLParser(RegistryMatcher matcher) {
        this.matcher = matcher;
    }


    public Rates parseFromString(String xmlString) throws ParserException {
        return parseToRates(xmlString, false);
    }

    public Rates parseFromFile(String filename) throws ParserException{
        return parseToRates(filename, true);
    }

    private Rates parseToRates(String source, boolean fromFile) throws ParserException {
        Rates rates;
        try{
            Persister serializer = new Persister(matcher);
            if(fromFile) {
                rates = serializer.read(Rates.class, new File(source), false);
            }else {
                rates = serializer.read(Rates.class, source, false);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ParserException(e.getMessage());
        }
        return rates;
    }

    public void serializeToFile(Rates rates, String filename)throws ParserException{
        try{
            Serializer serializer = new Persister(matcher);
            Writer writer = new FileWriter(filename);
            serializer.write(rates, writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new ParserException(e.getMessage());
        }

    }

}
