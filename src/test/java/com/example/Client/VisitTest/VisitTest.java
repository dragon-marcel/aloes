package com.example.Client.VisitTest;

import com.example.Client.entity.ItemVisit;
import com.example.Client.entity.Massage;
import com.example.Client.entity.Visit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
public class VisitTest {

    private Massage massage;
    private Visit visit;
    private List<ItemVisit> list;

    @Before
    public  void initialize(){
        massage = new Massage("massage",2.5);

        ItemVisit itemVisit = new ItemVisit(2,massage);
        ItemVisit itemVisit1 = new ItemVisit(1,massage);

        list = new ArrayList<>();
        list.add(itemVisit);
        list.add(itemVisit1);

        visit = new Visit();
        visit.setItems(list);
    }
    @Test
    public void checkIsNotNullVisit(){
        Assert.assertNotNull(visit);
    }

    @Test
    public void getTotalPriceTest(){

        Double expected = 7.5;
        Assert.assertEquals(expected,visit.getTotalPrice());
    }

    @Test
    public void countPriceTest(){

        Double expected = 5.0;
        Assert.assertEquals(expected,list.get(0).countPrice());
    }
}
