package se.yrgo.bidbroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.bidbroker.domain.SubmittedBid;

import java.util.List;

public interface SubmittedBidRepository extends JpaRepository<SubmittedBid, Long> {
    List<SubmittedBid> findByAuctionId(Long auctionId);
}
