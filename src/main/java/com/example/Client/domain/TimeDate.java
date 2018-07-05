package com.example.Client.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeDate {


    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();
    String datewithoutTime = sdf.format(today);

    public String getDatewithoutTime() {
        return datewithoutTime;
    }

    public void setDatewithoutTime(String datewithoutTime) {
        this.datewithoutTime = datewithoutTime;
    }
}