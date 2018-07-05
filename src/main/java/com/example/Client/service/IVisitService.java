package com.example.Client.service;

import com.example.Client.entity.Visit;

import java.util.List;

public interface IVisitService {
    public List<Visit> allVisitList();
    public List<Visit>getVisitbyData();
    public List<Visit>getVisitClose();
    public List<Visit>getVisitOpen();
}
