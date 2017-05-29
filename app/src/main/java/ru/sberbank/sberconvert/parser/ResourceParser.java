package ru.sberbank.sberconvert.parser;


import ru.sberbank.sberconvert.model.Rates;

/**
 * Created by kenneth on 28.05.17.
 */

public interface ResourceParser {

    Rates parseFromString(String resourceString) throws ParserException;
    Rates parseFromFile(String filename) throws ParserException;
    void serializeToFile(Rates rates, String filename) throws ParserException;
}
