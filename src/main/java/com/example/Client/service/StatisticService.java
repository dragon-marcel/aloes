package com.example.Client.service;

import com.example.Client.entity.ItemVisit;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import com.example.Client.repository.IVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

  @Autowired
  private IVisitRepository iVisitRepository;



  public Double getTotalValue(){
      List<Visit>listAllVisits = iVisitRepository.allVisitList();
      Double totalValue= 0.0;
      for (Visit listAllVisit : listAllVisits) {
          totalValue += listAllVisit.getTotalPrice();
      }
      return totalValue ;
  }
    public Double getCloseValue(){
        List<Visit>listCloseVisits = iVisitRepository.getVisitClose();
        Double totalCloseValue= 0.0;
        for (Visit listCloseVisit : listCloseVisits) {
            totalCloseValue += listCloseVisit.getTotalPrice();
        }
        return totalCloseValue ;
    }
public int getQuantityCloseVisit() {
   int resultclose = (int) iVisitRepository.allVisitList().stream().filter((a -> a.isStatus() == true)).count();
   return resultclose;
  }

public int getQuantityOpernVisit() {
        int resultOpen = (int) iVisitRepository.allVisitList().stream().filter((a -> a.isStatus() == false)).count();
        return resultOpen;
    }
}
