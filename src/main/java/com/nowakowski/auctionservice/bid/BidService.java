package com.nowakowski.auctionservice.bid;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    private final BidRepository repository;

    @Autowired
    public BidService(BidRepository repository) {
        this.repository = repository;
    }

    public Bid create(Bid user) {
        return repository.save(user);
    }

    Bid findOneBy(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
    }

    List<Bid> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    void update(Bid user) {
        repository.save(user);
    }

    void deleteBy(Long id) {
        repository.deleteById(id);
    }
}