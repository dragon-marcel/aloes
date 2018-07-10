package com.example.Client.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visits")
public class Visit implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotEmpty
private String description;

@ManyToOne(fetch = FetchType.LAZY)
private Client client;

private boolean sendEmail;

@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
@JoinColumn(name = "items_id")
private List<ItemVisit> items;

@Temporal(TemporalType.DATE)
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date createDate = new Date();

@Temporal(TemporalType.DATE)
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date visitDate = new Date();

@Temporal(TemporalType.TIME)
@DateTimeFormat(pattern = "kk:mm")
private Date visitTime = new Date();
private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Visit() {
        this.items = new ArrayList<ItemVisit>();
        this.status = false;
        this.sendEmail = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public List<ItemVisit> getItems() {
        return items;
    }

    public void setItems(List<ItemVisit> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrepion) {
        this.description = descrepion;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public void addItems(ItemVisit itemVisit){
        this.items.add(itemVisit);
    }
    public Double getTotalPrice(){
        Double total = 0.0;
        int size = items.size();

        for(int a = 0;a <size; a++){
            total +=items.get(a).countPrice();
        }return total;
    }
}

