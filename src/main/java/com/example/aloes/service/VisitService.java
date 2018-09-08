package com.example.aloes.service;

import com.example.aloes.entity.Visit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
public class VisitService {

    @PersistenceContext
    private EntityManager em;
    public boolean ifBusy(String visitDate,Date visitTime) {

        List<Visit> visits = em.createQuery("from Visit where visitDate ='" + visitDate + "'").getResultList();

        long count =  visits.stream().filter(a->a.getVisitTime().getHours() == visitTime.getHours()).count();

        if (count > 0) {
            return false;
        }
        return true;

    }
}
