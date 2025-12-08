package se.yrgo.auctionapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.auctionapi.domain.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
