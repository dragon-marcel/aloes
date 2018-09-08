package com.example.aloes.service;

import com.example.aloes.entity.Visit;
import com.example.aloes.repository.VisitR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

  @Autowired
  private VisitR visitR;



  public Double getTotalValue(){
      List<Visit>listAllVisits = visitR.allVisitList();
      Double totalValue= 0.0;
      for (Visit listAllVisit : listAllVisits) {
          totalValue += listAllVisit.getTotalPrice();
      }
      return totalValue ;
  }
    public Double getCloseValue(){
        List<Visit>listCloseVisits = visitR.getVisitClose();
        Double totalCloseValue= 0.0;
        for (Visit listCloseVisit : listCloseVisits) {
            totalCloseValue += listCloseVisit.getTotalPrice();
        }
        return totalCloseValue ;
    }
public int getQuantityCloseVisit() {
   int resultclose = (int) visitR.allVisitList().stream().filter((a -> a.isStatus() == true)).count();
   return resultclose;
  }

public int getQuantityOpernVisit() {
        int resultOpen = (int) visitR.allVisitList().stream().filter((a -> a.isStatus() == false)).count();
        return resultOpen;
    }
}
