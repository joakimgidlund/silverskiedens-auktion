package se.yrgo.auctionapi.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Auction {
    @Id
    @GeneratedValue
    private Long id;
    private double estimate;
    @ManyToOne
    @JoinColumn(name= "lot_id")
    private Lot lot;
    private Instant endTime;

    public Auction(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
