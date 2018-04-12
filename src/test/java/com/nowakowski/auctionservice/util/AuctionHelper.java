package com.nowakowski.auctionservice.util;

import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionUser;

import java.time.LocalDateTime;

import static com.nowakowski.auctionservice.model.AuctionType.FURNITURE;

public class AuctionHelper {

    public static Auction getExampleAuction() {
        return Auction
                .builder()
                .auctionType(FURNITURE)
                .creator(AuctionUser
                        .builder()
                        .name("Jon Doe")
                        .build())
                .description("second furniture")
                .startDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now().plusDays(7))
                .startingPrice(10L)
                .build();
    }
}
