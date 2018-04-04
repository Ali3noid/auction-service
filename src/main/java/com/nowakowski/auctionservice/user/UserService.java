package com.nowakowski.auctionservice.user;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.model.AuctionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public AuctionUser create(AuctionUser user) {
        return repository.save(user);
    }

    AuctionUser findOneBy(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    List<AuctionUser> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    void update(AuctionUser user) {
        repository.save(user);
    }

    void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
