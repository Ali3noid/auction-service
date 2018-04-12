package com.nowakowski.auctionservice.auction;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.exception.MissingDetailException;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.exception.WrongDetailException;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionDescriptionOnly;
import com.nowakowski.auctionservice.model.AuctionStartingPriceOnly;
import com.nowakowski.auctionservice.model.AuctionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class AuctionService {
    private final AuctionRepository repository;

    @Autowired
    public AuctionService(AuctionRepository repository) {
        this.repository = repository;
    }

    public Auction create(Auction auction) {
        validateDescription(auction.getDescription());
        validateStartingPrice(auction.getStartingPrice());
        validateCreator(auction.getCreator());
        validateDates(auction);
        return repository.save(auction);
    }

    public List<Auction> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    Auction findOne(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auction not found"));
    }

    void updateDescription(Long id, AuctionDescriptionOnly auction) {
        validateDescription(auction.getDescription());
        repository.updateDescription(id, auction);
    }

    void updateStartingPrice(Long id, AuctionStartingPriceOnly auction) {
        validateStartingPrice(auction.getStartingPrice());
        repository.updateStartingPrice(id, auction);
    }

    void deleteOne(Long id) {
        validateAuctionExist(id);
        repository.deleteById(id);
    }

    void validateAuctionExist(Long id) {
        if (!repository.existsById(id))
            throw new ResourceNotFoundException("Auction not found");
    }

    private void validateStartingPrice(Long price) {
        if (price == null)
            throw new MissingDetailException("Starting price must be given");
        if (price < 0)
            throw new WrongDetailException("Starting price must be non-negative");
    }

    private void validateCreator(AuctionUser auctionUser) {
        if (auctionUser == null)
            throw new MissingDetailException("Creator of auction is undefined");
    }

    private void validateDescription(String description) {
        if (isEmpty(description))
            throw new MissingDetailException("Empty description is not allowed");
    }

    private void validateDates(Auction auction) {
        if (auction.getFinishDate() == null || auction.getStartDate() == null)
            throw new MissingDetailException("Starting and finish date must be given");
        if (isStartDateAfterFinishDate(auction))
            throw new WrongDetailException("Finish date can't be before start date");
    }

    private boolean isStartDateAfterFinishDate(Auction auction) {
        return auction.getStartDate().compareTo(auction.getFinishDate()) > 0;
    }
}
