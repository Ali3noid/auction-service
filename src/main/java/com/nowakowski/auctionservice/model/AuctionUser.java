package com.nowakowski.auctionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String name;

    public AuctionUser(String name) {
        this.name = name;
    }

    //<editor-fold desc="Getters & Setters">
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //</editor-fold>
}
