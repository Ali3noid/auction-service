package com.nowakowski.auctionservice.user;

import com.nowakowski.auctionservice.model.AuctionUser;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<AuctionUser, Long> {
//
}
