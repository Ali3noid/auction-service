package com.nowakowski.auctionservice.auction;

import com.nowakowski.auctionservice.model.Auction;
import org.springframework.data.repository.CrudRepository;

interface AuctionRepository extends CrudRepository<Auction, Long> {
//
}