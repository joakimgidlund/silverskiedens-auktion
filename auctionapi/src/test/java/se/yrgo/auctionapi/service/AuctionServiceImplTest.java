package se.yrgo.auctionapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.BidRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.domain.Bid;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.AuctionDTO;
import se.yrgo.auctionapi.dto.UpdateAuctionDTO;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceImplTest {
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private LotRepository lotRepository;
    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Test
    void updateAuctionReturnsDto() {
        Long auctionId = 1L;

        Auction existing = new Auction();
        Lot attachedLot = new Lot();
        existing.setLot(attachedLot);
        existing.setId(auctionId);
        existing.setEstimate(BigDecimal.valueOf(1000));

        UpdateAuctionDTO updated = new UpdateAuctionDTO();
        updated.setEstimate(BigDecimal.valueOf(1500));
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(existing));
        when(auctionRepository.save(any(Auction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuctionDTO result = auctionService.updateAuction(auctionId, updated);

        assertThat(result.getEstimate()).isEqualTo(updated.getEstimate());

    }

    @Test
    void updateAuctionThrowsExceptionWhenAuctionIsNotFound() {
        Long auctionId = 2L;
        UpdateAuctionDTO dto = new UpdateAuctionDTO();
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> auctionService.updateAuction(auctionId, dto)).isInstanceOf(RuntimeException.class)
                .hasMessage("Auction not found");

    }

    @Test
    void placeBidSuccessful() {
        Long auctionId = 1L;
        Long userId = 42L;
        BigDecimal bidAmount = BigDecimal.valueOf(150);

        Auction auction = new Auction();
        auction.setId(auctionId);
        auction.setEndTime(Instant.now().plusSeconds(3600));
        auction.setCurrentBid(BigDecimal.valueOf(100));

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(bidRepository.findHighestBidAmountByAuctionId(auctionId)).thenReturn(Optional.of(BigDecimal.valueOf(100)));
        when(bidRepository.save(any(Bid.class))).thenAnswer(i -> i.getArgument(0));
        when(auctionRepository.save(any(Auction.class))).thenAnswer(i -> i.getArgument(0));

        auctionService.placeBid(auctionId, userId, bidAmount);

        verify(bidRepository).save(any(Bid.class));
        verify(auctionRepository).save(auction);
        assertThat(auction.getCurrentBid()).isEqualByComparingTo(bidAmount);
    }

}
