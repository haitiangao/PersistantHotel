package com.example.persistenthotel;

import java.io.Serializable;
import java.util.Date;

public class Guest implements Serializable {
    private String prefix;
    private String actualName;
    private String dateMade;

    public Guest(String prefix, String actualName, String dateMade){
        this.prefix=prefix;
        this.actualName=actualName;
        this.dateMade=dateMade;
    }
    public String getPrefix(){
        return prefix;
    }
    public void setPrefix(String prefix){
        this.prefix=prefix;
    }

    public String getActualName(){
        return actualName;
    }

    public void setActualName(String actualName){
        this.actualName=actualName;
    }

    public String getDateMade(){return dateMade;}
    public void setDateMade(String dateMade){
        this.dateMade=dateMade;
    }


}
