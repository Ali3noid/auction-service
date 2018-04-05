package com.nowakowski.auctionservice.bid;

import com.nowakowski.auctionservice.model.Bid;
import org.springframework.data.repository.CrudRepository;

interface BidRepository extends CrudRepository<Bid, Long> {
//
}