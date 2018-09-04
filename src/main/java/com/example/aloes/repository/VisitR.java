package com.example.aloes.repository;

import  com.example.aloes.entity.Visit;

import java.util.List;

public interface VisitR {
     void saveVisit(Visit visit);
     Visit findVisitById(Long id);
     void deleteVisitById(Long id);
     List<Visit>allVisitList();
     List<Visit>getVisitbyData();
     List<Visit>getVisitClose();
     List<Visit>getVisitOpen();
}
