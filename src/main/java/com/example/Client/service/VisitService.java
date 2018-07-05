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

}
