package se.yrgo.auctionapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.yrgo.auctionapi.domain.Auction;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a JOIN FETCH a.lot")
    List<Auction> findAllWithLot();

    void deleteByLotId(Long id);
    Optional<Auction> findByLotId(Long id);
}
