package com.example.Client.service;

import com.example.Client.dao.IVisitDAO;
import com.example.Client.domain.TimeDate;
import com.example.Client.entity.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
@Service
public class VisitService implements IVisitService {
    @Autowired
    private IVisitDAO iVisitDAO;

    @Autowired
    private TimeDate timeDate;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveVisit(Visit visit) {
        iVisitDAO.save(visit);
    }

    @Override
    public Visit findVisitById(Long id) {
        return iVisitDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteVisit(Long id) {
        iVisitDAO.deleteById(id);
    }

    @Override
    public List<Visit> allVisitList() {
        return (List<Visit>) iVisitDAO.findAll();
    }

    @Transactional
    public List<Visit>getVisitbyData(){

        String date = timeDate.getDatewithoutTime().toString();
        List<Visit>list = em.createQuery("from Visit where visitDate = '" + date+ "' Order by visitDate ASC").getResultList();

        return list;
    }

    @Override
    public List<Visit> getVisitClose() {
        return em.createQuery("from Visit where status = true ORDER BY visitDate").getResultList();
    }

    @Override
    public List<Visit> getVisitOpen() {
        return em.createQuery("from Visit where status = false ORDER BY visitDate").getResultList();

    }
    @Override
    public boolean checkifBusyDataAndTime(String visitDate,Date visitTime) {
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


