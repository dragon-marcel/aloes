package com.example.Client.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.util.Date;

@Component
@Scope(value ="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimeSession {
    
    @DateTimeFormat(pattern = "hh-mm-ss")
    @Temporal(TemporalType.TIME)
    public Date time= new Date();

    public String getTime() {
       String timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(time);
        return timeFormat;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
