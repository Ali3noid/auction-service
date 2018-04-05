package com.nowakowski.auctionservice.bid;

import com.google.common.base.Preconditions;
import com.nowakowski.auctionservice.model.Bid;
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
@RequestMapping(path = "bid")
public class BidController {

    private final BidService service;

    @Autowired
    public BidController(BidService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<Bid> retrieveAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Bid retrieveOne(@PathVariable("id") Long id) {
        return service.findOneBy(id);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Bid bid) {
        Preconditions.checkNotNull(bid);
        return service.create(bid)
                .getBidId();
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Bid bid) {
        Preconditions.checkNotNull(bid);
        service.findOneBy(bid.getBidId());
        service.update(bid);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteBy(id);
    }
}
