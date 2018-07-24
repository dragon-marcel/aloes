package com.example.Client.service;

import com.example.Client.entity.Visit;

import java.util.Date;
import java.util.List;

public interface IVisitService {
     void saveVisit(Visit visit);
     Visit findVisitById(Long id);
     void deleteVisit(Long id);
     List<Visit> allVisitList();
     List<Visit>getVisitbyData();
     List<Visit>getVisitClose();
     List<Visit>getVisitOpen();
     boolean checkifBusyDataAndTime(String visitDate,Date visitTime);
}
