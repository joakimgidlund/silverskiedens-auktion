CREATE TABLE submitted_bid (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    bid_amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);