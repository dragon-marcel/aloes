package com.example.Client.repository;

import  com.example.Client.entity.Visit;

import java.util.Date;
import java.util.List;

public interface IVisitRepository {
     void saveVisit(Visit visit);
     Visit findVisitById(Long id);
     void deleteVisitById(Long id);
     List<Visit> allVisitList();
     List<Visit>getVisitbyData();
     List<Visit>getVisitClose();
     List<Visit>getVisitOpen();
}
