package com.example.aloes.repository;

import com.example.aloes.dao.VisitDAO;
import com.example.aloes.domain.TimeDate;
import com.example.aloes.entity.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VisitRepository implements VisitR {

    @Autowired
    private VisitDAO visitDAO;

    @Autowired
    private TimeDate timeDate;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveVisit(Visit visit) {
        visitDAO.save(visit);
    }

    @Override
    public Visit findVisitById(Long id) {
        return visitDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteVisitById(Long id) {
        visitDAO.deleteById(id);
    }

    @Override
    public List<Visit> allVisitList() {
        return (List<Visit>) visitDAO.findAll();
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


