package com.nowakowski.auctionservice.auction;

import com.nowakowski.auctionservice.exception.MissingDetailException;
import com.nowakowski.auctionservice.exception.ResourceNotFoundException;
import com.nowakowski.auctionservice.exception.WrongDetailException;
import com.nowakowski.auctionservice.model.Auction;
import com.nowakowski.auctionservice.model.AuctionDescriptionOnly;
import com.nowakowski.auctionservice.model.AuctionStartingPriceOnly;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.nowakowski.auctionservice.util.AuctionHelper.getExampleAuction;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTest {

    private static final long NEGATIVE_PRICE = -100L;
    private static final long EXAMPLE_ID = 1L;
    @Mock
    private AuctionRepository repository;

    @InjectMocks
    private AuctionService service;

    @Test
    public void whenCreateAuction_thenReturnAuction() {
        // given
        Auction auction = getExampleAuction();
        when(repository.save(auction)).thenReturn(auction);

        // when
        Auction createdAuction = service.create(auction);

        // then
        assertThat(createdAuction).isEqualToComparingFieldByFieldRecursively(auction);
    }

    @Test
    public void whenCreateAuctionWithNullDescription_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setDescription(null);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Empty description is not allowed");
    }

    @Test
    public void whenCreateAuctionWithEmptyDescription_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setDescription(EMPTY);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Empty description is not allowed");
    }

    @Test
    public void whenCreateAuctionWithNullStartingPrice_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setStartingPrice(null);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Starting price must be given");
    }

    @Test
    public void whenCreateAuctionWithNegativeStartingPrice_thenThrowWrongDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setStartingPrice(NEGATIVE_PRICE);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(WrongDetailException.class)
                .hasMessageContaining("Starting price must be non-negative");
    }

    @Test
    public void whenCreateAuctionWithNullCreator_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setCreator(null);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Creator of auction is undefined");
    }

    @Test
    public void whenCreateAuctionWithNullStartDate_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setStartDate(null);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Starting and finish date must be given");
    }

    @Test
    public void whenCreateAuctionWithNullFinishDate_thenThrowMissingDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setFinishDate(null);

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Starting and finish date must be given");
    }

    @Test
    public void whenCreateAuctionWithWrongDates_thenThrowWrongDetailException() {
        // given
        Auction auction = getExampleAuction();
        auction.setFinishDate(LocalDateTime.now());
        auction.setFinishDate(LocalDateTime.now().minusDays(1));

        // when
        Throwable thrown = catchThrowable(() -> service.create(auction));

        // then
        assertThat(thrown).isInstanceOf(WrongDetailException.class)
                .hasMessageContaining("Finish date can't be before start date");
    }

    @Test
    public void whenFindAll_thenReturnList() {
        // given
        List<Auction> expectedAuctions = new ArrayList<>();
        expectedAuctions.add(getExampleAuction());
        Iterator<Auction> iterator = expectedAuctions.iterator();
        Iterable<Auction> iterable = () -> iterator;
        when(repository.findAll()).thenReturn(iterable);

        // when
        List<Auction> actualAuctions = service.findAll();

        // then
        assertThat(actualAuctions).hasSameElementsAs(expectedAuctions);
    }

    @Test
    public void whenFindOneThatExists_thenReturnAuction() {
        // given
        Auction expectedAuction = getExampleAuction();
        Optional<Auction> optionalAuction = Optional.of(expectedAuction);
        when(repository.findById(EXAMPLE_ID)).thenReturn(optionalAuction);

        // when
        Auction actualAuction = service.findOne(EXAMPLE_ID);

        //then
        assertThat(actualAuction).isEqualToComparingFieldByFieldRecursively(expectedAuction);
    }

    @Test
    public void whenFindOneThatNotExists_thenThrowResourceNotFoundException() {
        // given
        Optional<Auction> optionalAuction = Optional.empty();
        when(repository.findById(EXAMPLE_ID)).thenReturn(optionalAuction);

        // when
        Throwable thrown = catchThrowable(() -> service.findOne(EXAMPLE_ID));

        //then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Auction not found");
    }

    @Test
    public void whenUpdateAuctionWithNullDescription_thenThrowMissingDetailException() {
        // given
        AuctionDescriptionOnly descriptionOnly = AuctionDescriptionOnly
                .builder()
                .description(null)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> service.updateDescription(EXAMPLE_ID, descriptionOnly));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Empty description is not allowed");
    }

    @Test
    public void whenUpdateAuctionWithEmptyDescription_thenThrowMissingDetailException() {
        // given
        AuctionDescriptionOnly descriptionOnly = AuctionDescriptionOnly
                .builder()
                .description(EMPTY)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> service.updateDescription(EXAMPLE_ID, descriptionOnly));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Empty description is not allowed");
    }

    @Test
    public void whenUpdateAuctionWithNullStartingPrice_thenThrowMissingDetailException() {
        // given
        AuctionStartingPriceOnly startingPriceOnly = AuctionStartingPriceOnly
                .builder()
                .startingPrice(null)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> service.updateStartingPrice(EXAMPLE_ID, startingPriceOnly));

        // then
        assertThat(thrown).isInstanceOf(MissingDetailException.class)
                .hasMessageContaining("Starting price must be given");
    }

    @Test
    public void whenUpdateAuctionWithNegativePrice_thenThrowWrongDetailException() {
        // given
        AuctionStartingPriceOnly startingPriceOnly = AuctionStartingPriceOnly
                .builder()
                .startingPrice(NEGATIVE_PRICE)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> service.updateStartingPrice(EXAMPLE_ID, startingPriceOnly));

        // then
        assertThat(thrown).isInstanceOf(WrongDetailException.class)
                .hasMessageContaining("Starting price must be non-negative");
    }

    @Test
    public void whenValidateAuctionNotExists_thenThrowResourceNotFoundException() {
        // given
        when(repository.existsById(EXAMPLE_ID)).thenReturn(FALSE);

        // when
        Throwable thrown = catchThrowable(() -> service.deleteOne(EXAMPLE_ID));

        //then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Auction not found");
    }

}