package ru.sberbank.sberconvert.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.List;

/**
 * Created by kenneth on 25.05.17.
 */


@Root(name="ValCurs" )
public class Rates {

    private String name;
    private List<Currency> currencies;
    private Date date;


    @Attribute(name="Date")
    public Date getDate() {
        return date;
    }

    @Attribute(name="Date")
    public void setDate(Date date) {
        this.date = date;
    }

    @Attribute(name="name" )
    public String getName() {
        return name;
    }

    @Attribute(name="name" )
    public void setName(String name) {
        this.name = name;
    }

    @ElementList(entry="Valute", inline=true )
    public List<Currency> getCurrencies() {
        return currencies;
    }

    @ElementList(entry="Valute", inline=true)
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

}
