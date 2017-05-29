package ru.sberbank.sberconvert.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by kenneth on 25.05.17.
 */

@Element(name="Valute")
public class Currency {

    private String ID;
    private int numCode;
    private String charCode;
    private int nominal;
    private String name;
    private double value;


    @Attribute(name="ID" )
    public String getID() {
        return ID;
    }

    @Attribute(name="ID" )
    public void setID(String ID) {
        this.ID = ID;
    }

    @Element(name="NumCode")
    public int getNumCode() {
        return numCode;
    }

    @Element(name="NumCode")
    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    @Element(name="CharCode")
    public String getCharCode() {
        return charCode;
    }

    @Element(name="CharCode")
    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    @Element(name="Nominal")
    public int getNominal() {
        return nominal;
    }

    @Element(name="Nominal")
    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Element(name="Name")
    public String getName() {
        return name;
    }

    @Element(name="Name")
    public void setName(String name) {
        this.name = name;
    }

    @Element(name="Value")
    public Double getValue() {
        return value;
    }

    @Element(name="Value")
    public void setValue(Double value) {
        this.value = value;
    }

    public double getRatio(){
        return value / nominal;
    }
}
