package com.example.Client.service;

import com.example.Client.entity.ItemVisit;
import com.example.Client.entity.Visit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VisitService {

    @PersistenceContext
    private EntityManager em;
    public boolean checkIfBusyDataAndTime(String visitDate,Date visitTime) {
        List<Visit> listVisit = em.createQuery("from Visit where visitDate ='" + visitDate + "'").getResultList();

        if (listVisit.size() > 0) {
            for (Visit visit :listVisit){
                if(visit.getVisitTime().getHours() == visitTime.getHours() ){
                    return false;
                }else {
                    return true;
                }
            }

        }return true;

    }
}
