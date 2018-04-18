package com.nowakowski.auctionservice.auction;

import com.google.common.base.Preconditions;
import com.nowakowski.auctionservice.bid.BidService;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionDescriptionOnly;
import com.nowakowski.auctionservice.model.AuctionStartingPriceOnly;
import com.nowakowski.auctionservice.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final BidService bidService;

    @Autowired
    public AuctionController(AuctionService auctionService, BidService bidService) {
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    //<editor-fold desc="create">
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAuction(@RequestBody Auction auction) {
        Preconditions.checkNotNull(auction);
        return auctionService.create(auction)
                .getAuctionId();
    }

    @PostMapping(value = "/{auctionId}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createBid(@PathVariable("auctionId") Long auctionId, @RequestBody Bid bid) {
        Preconditions.checkNotNull(bid);
        Auction auction = auctionService.findOne(auctionId);
        return bidService.create(bid, auction)
                .getBidId();
    }
    //</editor-fold>

    //<editor-fold desc="retrieve">
    @GetMapping
    public List<Auction> retrieveAllAuctions() {
        return auctionService.findAll();
    }

    @GetMapping(value = "/{auctionId}")
    public Auction retrieveOneAuction(@PathVariable("auctionId") Long auctionId) {
        return auctionService.findOne(auctionId);
    }

    @GetMapping(value = "/{auctionId}/bid")
    public List<Bid> retrieveBidsBy(@PathVariable("auctionId") Long auctionId) {
        auctionService.validateAuctionExist(auctionId);
        return bidService.findBy(auctionId);
    }

    @GetMapping(value = "/{auctionId}/bid/{bidId}")
    public Bid retrieveOneBid(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.validateAuctionExist(auctionId);
        return bidService.findOneBy(bidId);
    }
    //</editor-fold>

    //<editor-fold desc="update-actions">
    @PatchMapping(value = "/{auctionId}/description")
    public void updateDescription(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionDescriptionOnly descriptionOnly) {
        Preconditions.checkNotNull(descriptionOnly);
        auctionService.updateDescription(auctionId, descriptionOnly);
    }

    @PatchMapping(value = "/{auctionId}/starting-price")
    public void updateStartingPrice(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionStartingPriceOnly priceOnly) {
        Preconditions.checkNotNull(priceOnly);
        auctionService.updateStartingPrice(auctionId, priceOnly);
    }
    //</editor-fold>

    //<editor-fold desc="deleteOne-actions">
    @DeleteMapping(value = "/{auctionId}/bid/{bidId}")
    public void deleteBid(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.validateAuctionExist(auctionId);
        bidService.deleteBy(bidId);
    }

    @DeleteMapping(value = "/{auctionId}")
    public void deleteAuction(@PathVariable("auctionId") Long auctionId) {
        auctionService.deleteOne(auctionId);
    }
    //</editor-fold>
}
