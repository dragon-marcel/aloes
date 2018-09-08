package com.example.aloes.domain;

import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeDate {

    Date today = new Date();

    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateWithOutTime = sdf.format(today);

    public String getDatewithoutTime() {
        return dateWithOutTime;
    }

}