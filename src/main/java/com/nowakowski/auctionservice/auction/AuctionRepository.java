package com.nowakowski.auctionservice.auction;

import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionDescriptionOnly;
import com.nowakowski.auctionservice.model.AuctionStartingPriceOnly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuctionRepository extends CrudRepository<Auction, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Auction a SET a.description = :#{#auction.description} WHERE a.auctionId = :id")
    void updateDescription(@Param("id") Long id, @Param("auction") AuctionDescriptionOnly auction);

    @Transactional
    @Modifying
    @Query("UPDATE Auction a SET a.startingPrice = :#{#auction.startingPrice} WHERE a.auctionId = :id")
    void updateStartingPrice(@Param("id") Long id, @Param("auction") AuctionStartingPriceOnly auction);
}