package se.yrgo.auctionapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.yrgo.auctionapi.domain.Bid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionId(Long auctionId);

    @Query("SELECT COALESCE(MAX(b.bidAmount), 0) FROM Bid b WHERE b.auction.id = :auctionId")
    Optional<BigDecimal> findHighestBidAmountByAuctionId(@Param("auctionId") Long auctionId);

    void deleteByAuctionId(Long auctionId);
}
