package se.yrgo.auctionapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.auctionapi.domain.Lot;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
}
