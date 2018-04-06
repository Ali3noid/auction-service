package com.nowakowski.auctionservice.filler;

import com.nowakowski.auctionservice.auction.AuctionService;
import com.nowakowski.auctionservice.bid.BidService;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionUser;
import com.nowakowski.auctionservice.model.Bid;
import com.nowakowski.auctionservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.nowakowski.auctionservice.model.AuctionType.BOOKS;
import static com.nowakowski.auctionservice.model.AuctionType.CLOTHES;
import static com.nowakowski.auctionservice.model.AuctionType.FURNITURE;
import static com.nowakowski.auctionservice.model.AuctionType.TOYS;

@RestController
@RequestMapping(path = "filler")
public class FillerController {

    private static final long WEEK = 7L;
    private static final String HARRY_POTTER = "Harry Potter";
    private final UserService userService;
    private final AuctionService auctionService;
    private final BidService bidService;

    @Autowired
    public FillerController(UserService userService, AuctionService auctionService, BidService bidService) {
        this.userService = userService;
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public String fillUserTable() {
        userService.create(AuctionUser.builder().name("Adam Bartczak").build());
        userService.create(AuctionUser.builder().name("Czesław Dębski").build());
        userService.create(AuctionUser.builder().name("Edward Filipiak").build());
        userService.create(AuctionUser.builder().name("Grzegorz Herbicki").build());
        return "Users were added";
    }

    @GetMapping(value = "/auction")
    @ResponseBody
    public String fillAuctionTable() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime finishDate = startDate.plusDays(WEEK);
        List<AuctionUser> users = userService.findAll().stream().sorted(Comparator.comparing(AuctionUser::getUserId)).collect(Collectors.toList());
        auctionService.create(Auction
                .builder()
                .auctionType(BOOKS)
                .creator(users.get(0))
                .description(HARRY_POTTER)
                .startDate(startDate)
                .finishDate(finishDate)
                .startingPrice(10L)
                .build());
        auctionService.create(Auction
                .builder()
                .auctionType(CLOTHES)
                .creator(users.get(0))
                .description("Sweater XL")
                .startDate(startDate)
                .finishDate(finishDate)
                .startingPrice(35L)
                .build());
        auctionService.create(Auction
                .builder()
                .auctionType(FURNITURE)
                .creator(users.get(2))
                .description("4 chairs")
                .startDate(startDate)
                .finishDate(finishDate)
                .startingPrice(330L)
                .build());
        auctionService.create(Auction
                .builder()
                .auctionType(TOYS)
                .creator(users.get(3))
                .description("Lego")
                .startDate(startDate)
                .finishDate(finishDate)
                .startingPrice(110L)
                .build());
        return "Auctions were added";
    }

    @GetMapping(value = "/bid")
    @ResponseBody
    public String fillBidTable() {
        LocalDateTime now = LocalDateTime.now();
        List<AuctionUser> users = userService.findAll().stream().sorted(Comparator.comparing(AuctionUser::getUserId)).collect(Collectors.toList());
        Auction harryPotterAuction = auctionService
                .findAll()
                .stream()
                .filter(auction -> auction.getDescription().equals(HARRY_POTTER))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Auction not found"));
        bidService.create(Bid
                .builder()
                .bidder(users.get(1))
                .creationDate(now.plusDays(1))
                .auction(harryPotterAuction)
                .price(12L)
                .build());
        bidService.create(Bid
                .builder()
                .bidder(users.get(2))
                .creationDate(now.plusDays(2))
                .auction(harryPotterAuction)
                .price(25L)
                .build());
        bidService.create(Bid
                .builder()
                .bidder(users.get(1))
                .creationDate(now.plusDays(3))
                .auction(harryPotterAuction)
                .price(47L)
                .build());
        return "Bids were added";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public String fillAllTables() {
        fillUserTable();
        fillAuctionTable();
        fillBidTable();
        return "All tables were filled";
    }

}
