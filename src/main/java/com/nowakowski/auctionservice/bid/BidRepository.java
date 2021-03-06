package com.nowakowski.auctionservice.bid;

import com.nowakowski.auctionservice.model.Bid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface BidRepository extends CrudRepository<Bid, Long> {

    @Query("from Bid  b where b.auction.auctionId=:auctionId")
    Iterable<Bid> findBy(@Param("auctionId") Long auctionId);

    @Modifying
    @Query("UPDATE Auction a SET a.currentPrice = :newPrice WHERE a.auctionId = :id")
    void updateCurrentPrice(@Param("id") Long id, @Param("newPrice") Long newPrice);
}