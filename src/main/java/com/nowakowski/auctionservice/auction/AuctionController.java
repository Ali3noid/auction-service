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

    @GetMapping(value = "/{auctionId}/bid")
    @ResponseBody
    public List<Bid> retrieveAll(@PathVariable("auctionId") Long auctionId) {
        return bidService.findBy(auctionId);
    }

    @GetMapping(value = "/{auctionId}/bid/{bidId}")
    @ResponseBody
    public Bid retrieveOne(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.findOneBy(auctionId);
        return bidService.findOneBy(bidId);
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

    @DeleteMapping(value = "/{auctionId}/bid/{bidId}")
    public void delete(@PathVariable("auctionId") Long auctionId, @PathVariable("bidId") Long bidId) {
        auctionService.findOneBy(auctionId);
        bidService.deleteBy(bidId);
    }

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

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Auction auction) {
        Preconditions.checkNotNull(auction);
        return auctionService.create(auction)
                .getAuctionId();
    }

    @PatchMapping(value = "/{auctionId}", params = "update-description")
    public void updateDescription(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionDescriptionOnly descriptionOnly) {
        Preconditions.checkNotNull(descriptionOnly);
        auctionService.findOneBy(auctionId);
        auctionService.updateDescription(auctionId, descriptionOnly);
    }

    @PatchMapping(value = "/{auctionId}", params = "update-starting-price")
    public void updateStartingPrice(@PathVariable("auctionId") Long auctionId, @RequestBody AuctionStartingPriceOnly priceOnly) {
        Preconditions.checkNotNull(priceOnly);
        auctionService.findOneBy(auctionId);
        auctionService.updateStartingPrice(auctionId, priceOnly);
    }

    @DeleteMapping(value = "/{auctionId}")
    public void delete(@PathVariable("auctionId") Long auctionId) {
        auctionService.deleteBy(auctionId);
    }
}
