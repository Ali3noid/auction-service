package com.nowakowski.auctionservice.bid;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.auction.AuctionRepository;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.exception.WrongDetailException;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BidService {
    private final BidRepository bidRepository;

    @Autowired
    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Transactional
    public Bid create(Bid bid, Auction auction) {
        Long newPrice = bid.getPrice();
        validateBiddingOwnAuction(bid, auction);
        validateNonNegativeBid(newPrice);
        validateGreaterBidThanCurrentPrice(auction.getCurrentPrice(), newPrice);
        bidRepository.updateCurrentPrice(auction.getAuctionId(), newPrice);
        return bidRepository.save(bid);
    }

    public Bid findOneBy(Long id) {
        return bidRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
    }

    public void deleteBy(Long bidId) {
        bidRepository.deleteById(bidId);
    }

    public List<Bid> findBy(Long auctionId) {
        return Lists.newArrayList(bidRepository.findBy(auctionId));
    }

    private void validateBiddingOwnAuction(Bid bid, Auction auction) {
        if (auction.getCreator().getUserId().equals(bid.getBidder().getUserId()))
            throw new WrongDetailException("Bidding own auctions is not allowed");
    }

    private void validateGreaterBidThanCurrentPrice(Long currentPrice, Long newPrice) {
        if (currentPrice != null && newPrice <= currentPrice)
            throw new WrongDetailException("Bid must be bigger than current price");
    }

    private void validateNonNegativeBid(Long newPrice) {
        if (newPrice < 0) throw new WrongDetailException("Bid cannot be negative value");
    }
}
