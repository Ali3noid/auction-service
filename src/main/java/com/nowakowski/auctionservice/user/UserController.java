package com.nowakowski.auctionservice.user;

import com.google.common.base.Preconditions;
import com.nowakowski.auctionservice.model.AuctionUser;
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
@RequestMapping(path = "user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<AuctionUser> retrieveAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public AuctionUser retrieveOne(@PathVariable("id") Long id) {
        return service.findOneBy(id);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody AuctionUser auctionUser) {
        Preconditions.checkNotNull(auctionUser);
        return service.create(auctionUser)
                .getUserId();
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody AuctionUser auctionUser) {
        Preconditions.checkNotNull(auctionUser);
        service.findOneBy(auctionUser.getUserId());
        service.update(auctionUser);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteBy(id);
    }
}
