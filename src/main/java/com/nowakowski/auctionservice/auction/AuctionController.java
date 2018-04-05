package com.nowakowski.auctionservice.auction;

import com.google.common.base.Preconditions;
import com.nowakowski.auctionservice.model.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "auction")
public class AuctionController {

    private final AuctionService service;

    @Autowired
    public AuctionController(AuctionService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<Auction> retrieveAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Auction retrieveOne(@PathVariable("id") Long id) {
        return service.findOneBy(id);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Auction auction) {
        Preconditions.checkNotNull(auction);
        return service.create(auction)
                .getAuctionId();
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Auction auction) {
        Preconditions.checkNotNull(auction);
        service.findOneBy(auction.getAuctionId());
        service.update(auction);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteBy(id);
    }
}
