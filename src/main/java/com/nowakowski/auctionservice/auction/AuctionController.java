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
import org.springframework.web.bind.annotation.ResponseBody;
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

    //<editor-fold desc="create-actions">
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Auction auction) {
        Preconditions.checkNotNull(auction);
        return auctionService.create(auction)
                .getAuctionId();
    }

    @PostMapping(value = "/{auctionId}/bid")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@PathVariable("auctionId") Long auctionId, @RequestBody Bid bid) {
        auctionService.findOneBy(auctionId);
        Preconditions.checkNotNull(bid);
        return bidService.create(bid)
                .getBidId();
    }
    //</editor-fold>

    //<editor-fold desc="retrieve-actions">
    @GetMapping
    @ResponseBody
    public List<Auction> retrieveAll() {
        return auctionService.findAll();
    }

    @GetMapping(value = "/{auctionId}")
    @ResponseBody
    public Auction retrieveOne(@PathVariable("auctionId") Long auctionId) {
        return auctionService.findOneBy(auctionId);
    }

    @GetMapping(value = "/{auctionId}/bid")
    @ResponseBody
    public List<Bid> retrieveBy(@PathVariable("auctionId") Long auctionId) {
        auctionService.checkIfAuctionExist(auctionId);
        return bidService.findBy(auctionId);
    }

    @GetMapping(value = "/{auctionId}/bid/{bidId}")
    @ResponseBody
    public Bid retrieveOne(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.checkIfAuctionExist(auctionId);
        return bidService.findOneBy(bidId);
    }
    //</editor-fold>

    //<editor-fold desc="update-actions">
    @PatchMapping(value = "/{auctionId}/description")
    public void updateDescription(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionDescriptionOnly descriptionOnly) {
        Preconditions.checkNotNull(descriptionOnly);
        auctionService.checkIfAuctionExist(auctionId);
        auctionService.updateDescription(auctionId, descriptionOnly);
    }

    @PatchMapping(value = "/{auctionId}/starting-price")
    public void updateStartingPrice(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionStartingPriceOnly priceOnly) {
        Preconditions.checkNotNull(priceOnly);
        auctionService.checkIfAuctionExist(auctionId);
        auctionService.updateStartingPrice(auctionId, priceOnly);
    }
    //</editor-fold>

    //<editor-fold desc="delete-actions">
    @DeleteMapping(value = "/{auctionId}/bid/{bidId}")
    public void delete(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.checkIfAuctionExist(auctionId);
        bidService.deleteBy(bidId);
    }

    @DeleteMapping(value = "/{auctionId}")
    public void delete(@PathVariable("auctionId") Long auctionId) {
        auctionService.deleteBy(auctionId);
    }
    //</editor-fold>
}
