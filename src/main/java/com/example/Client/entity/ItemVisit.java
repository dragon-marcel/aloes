package com.example.Client.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "items_visit")
    public class ItemVisit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "massage_id")
    private Massage massage;

    public Long getId() {
            return id;
        }
    public void setId(Long id) {
            this.id = id;
        }

    public Massage getMassage() {
        return massage;
    }
    public void setMassage(Massage massage) {
        this.massage = massage;
    }

    public Integer getQuantity() {
            return quantity;
        }

     public void setQuantity(Integer position) {
            this.quantity= position;
        }
     public Double countPrice() {
         return quantity.doubleValue() * massage.getPrice();
     }
}
