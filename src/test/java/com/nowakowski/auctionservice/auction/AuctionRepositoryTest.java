package com.nowakowski.auctionservice.auction;

import com.google.common.collect.Lists;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionDescriptionOnly;
import com.nowakowski.auctionservice.model.AuctionStartingPriceOnly;
import com.nowakowski.auctionservice.model.AuctionUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.nowakowski.auctionservice.model.AuctionType.BOOKS;
import static com.nowakowski.auctionservice.model.AuctionType.CLOTHES;
import static com.nowakowski.auctionservice.model.AuctionType.FURNITURE;
import static com.nowakowski.auctionservice.model.AuctionType.TOYS;
import static com.nowakowski.auctionservice.util.AuctionHelper.getExampleAuction;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AuctionRepositoryTest {

    private static final String NEW_DESCRIPTION = "third furniture";
    private static final long NEW_STARTING_PRICE = 900L;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuctionRepository auctionRepository;

    @Before
    public void setUp() {
        AuctionUser firstUser = AuctionUser
                .builder()
                .name("Jon Doe")
                .build();
        AuctionUser secondUser = AuctionUser
                .builder()
                .name("Another Jon Doe")
                .build();
        AuctionUser thirdUser = AuctionUser
                .builder()
                .name("And Another One Jon Doe")
                .build();
        Auction firstAuction = Auction
                .builder()
                .auctionType(BOOKS)
                .creator(firstUser)
                .description("book")
                .startDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now().plusDays(7))
                .startingPrice(10L)
                .build();
        Auction secondAuction = Auction
                .builder()
                .auctionType(TOYS)
                .creator(secondUser)
                .description("toy")
                .startDate(LocalDateTime.now().minusDays(1))
                .finishDate(LocalDateTime.now().plusDays(13))
                .startingPrice(20L)
                .build();
        Auction thirdAuction = Auction
                .builder()
                .auctionType(FURNITURE)
                .creator(thirdUser)
                .description("furniture")
                .startDate(LocalDateTime.now().plusDays(1))
                .finishDate(LocalDateTime.now().plusDays(7))
                .startingPrice(10L)
                .build();
        entityManager.persist(firstUser);
        entityManager.persist(secondUser);
        entityManager.persist(thirdUser);
        entityManager.persist(firstAuction);
        entityManager.persist(secondAuction);
        entityManager.persist(thirdAuction);
        entityManager.flush();
    }

    @Test
    public void whenSave_thenReturnAdditionalAuction() {
        // given
        Auction auction = getExampleAuction();

        // when
        auctionRepository.save(auction);
        List<Auction> foundAuctions = Lists.newArrayList(auctionRepository.findAll());

        // then
        assertThat(foundAuctions)
                .hasSize(4)
                .allMatch(el -> el.getCurrentPrice() == null)
                .allMatch(el -> el.getStartingPrice() != null && el.getStartingPrice() > 0)
                .allMatch(el -> !el.getAuctionType().equals(CLOTHES));
    }

    @Test
    public void whenFindAll_thenReturnAllAuctions() {
        // given
        Auction auction = getExampleAuction();
        entityManager.persist(auction);
        entityManager.flush();

        // when
        List<Auction> foundAuctions = Lists.newArrayList(auctionRepository.findAll());

        // then
        assertThat(foundAuctions)
                .hasSize(4)
                .allMatch(el -> el.getCurrentPrice() == null)
                .allMatch(el -> el.getStartingPrice() != null && el.getStartingPrice() > 0)
                .allMatch(el -> !el.getAuctionType().equals(CLOTHES));
    }

    @Test
    public void whenFindById_thenReturnAuction() {
        // given
        Auction auction = getExampleAuction();

        // when
        Auction createdAuction = auctionRepository.save(auction);
        Optional<Auction> foundAuction = auctionRepository.findById(createdAuction.getAuctionId());

        // then
        assertThat(foundAuction)
                .isPresent()
                .hasValue(createdAuction);
    }

    @Test
    public void whenUpdateDescription_thenReturnAuctionWithChangedDescription() {
        // given
        Auction auction = getExampleAuction();
        Auction createdAuction = auctionRepository.save(auction);
        Long auctionId = createdAuction.getAuctionId();

        // when
        auctionRepository.updateDescription(auctionId,
                AuctionDescriptionOnly
                        .builder()
                        .description(NEW_DESCRIPTION)
                        .build());
        Optional<Auction> foundAuction = auctionRepository.findById(auctionId);
        createdAuction.setDescription(NEW_DESCRIPTION);

        // then
        assertThat(foundAuction)
                .isPresent()
                .hasValue(createdAuction);
    }

    @Test
    public void whenUpdateStartingPrice_thenReturnAuctionWithChangedPrice() {
        // given
        Auction auction = getExampleAuction();
        Auction createdAuction = auctionRepository.save(auction);
        Long auctionId = createdAuction.getAuctionId();

        // when
        auctionRepository.updateStartingPrice(auctionId,
                AuctionStartingPriceOnly
                        .builder()
                        .startingPrice(NEW_STARTING_PRICE)
                        .build());
        Optional<Auction> foundAuction = auctionRepository.findById(auctionId);
        createdAuction.setStartingPrice(NEW_STARTING_PRICE);

        // then
        assertThat(foundAuction)
                .isPresent()
                .hasValue(createdAuction);
    }

    @Test
    public void whenDeletedById_thenReturnAuctionsWithoutDeletedOne() {
        // given
        Auction auction = getExampleAuction();
        Auction auctionToDelete = auctionRepository.save(auction);

        // when
        auctionRepository.deleteById(auctionToDelete.getAuctionId());
        List<Auction> foundAuctions = Lists.newArrayList(auctionRepository.findAll());

        // then
        assertThat(foundAuctions)
                .hasSize(3)
                .doesNotContain(auctionToDelete);
    }

    @Test
    public void whenExistsByIdForCreatedAuction_thenReturnTrue() {
        // given
        Auction auction = getExampleAuction();
        Auction createdAuction = auctionRepository.save(auction);

        // when
        Boolean check = auctionRepository.existsById(createdAuction.getAuctionId());

        // then
        assertThat(check)
                .isTrue();
    }


}