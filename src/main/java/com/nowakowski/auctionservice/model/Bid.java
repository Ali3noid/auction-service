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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bidId;

    @OneToOne
    @JoinColumn(name = "userId")
    @NotNull
    private AuctionUser bidder;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "auctionId")
    @NotNull
    private Auction auction;
}
