package se.yrgo.auctionapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.BidRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.LotDTO;
import se.yrgo.auctionapi.dto.UpdateLotDTO;

@ExtendWith(MockitoExtension.class)
public class LotServiceImplTest {
    @Mock
    private LotRepository lotRepository;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private LotServiceImpl lotService;

    @Test
    void updateLotReturnsDto() {
        Long lotId = 1L;

        Lot existing = new Lot();
        existing.setId(lotId);
        existing.setTitle("Old title");
        existing.setPublished(false);

        UpdateLotDTO updated = new UpdateLotDTO();
        updated.setTitle("New title");
        updated.setDescription("New dto");
        updated.setImagePath("/img/new.jpg");
        updated.setPublished(true);

        when(lotRepository.findById(lotId)).thenReturn(Optional.of(existing));

        when(lotRepository.save(any(Lot.class))).thenAnswer(invocation -> invocation.getArgument(0));
        LotDTO result = lotService.updateLot(lotId, updated);

        assertThat(result.getTitle()).isEqualTo(updated.getTitle());
        assertThat(result.isPublished()).isTrue();
        
    }

    @Test
    void updateLotThrowsExceptionWhenLotIsNotFound() {
        Long lotId = 2L;
        UpdateLotDTO dto = new UpdateLotDTO();
        when(lotRepository.findById(lotId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> lotService.updateLot(lotId, dto)).isInstanceOf(RuntimeException.class);    
    }

}
