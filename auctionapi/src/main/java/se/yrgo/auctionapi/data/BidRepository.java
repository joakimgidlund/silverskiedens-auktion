package se.yrgo.auctionapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.yrgo.auctionapi.domain.Bid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionId(Long auctionId);

    @Query("SELECT COALESCE(MAX(b.bid_amount, 0)) FROM Bid b WHERE b.auction_id = :auctionId")
    Optional<BigDecimal> findHighestBidAmountByAuctionId(Long auctionId);
}
