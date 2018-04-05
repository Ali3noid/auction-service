package com.nowakowski.auctionservice.auction;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.model.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository repository;

    @Autowired
    public AuctionService(AuctionRepository repository) {
        this.repository = repository;
    }

    public Auction create(Auction user) {
        return repository.save(user);
    }

    Auction findOneBy(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auction not found"));
    }

    public List<Auction> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    void update(Auction user) {
        repository.save(user);
    }

    void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
