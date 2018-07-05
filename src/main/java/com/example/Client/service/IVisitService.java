package com.example.Client.service;

import com.example.Client.entity.Visit;

import java.util.List;

public interface IVisitService {
     List<Visit> allVisitList();
     List<Visit>getVisitbyData();
     List<Visit>getVisitClose();
     List<Visit>getVisitOpen();
}
