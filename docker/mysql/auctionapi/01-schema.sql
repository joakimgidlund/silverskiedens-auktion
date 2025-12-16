CREATE TABLE lot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image_path VARCHAR(255),
    published BOOLEAN NOT NULL
);

CREATE TABLE auction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estimate DECIMAL(19,2),
    current_bid DECIMAL(19,2),
    end_time TIMESTAMP,
    lot_id BIGINT,
    CONSTRAINT fk_auction_lot
        FOREIGN KEY (lot_id) REFERENCES lot(id)
);

CREATE TABLE bid (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    bid_amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT fk_bid_auction
        FOREIGN KEY (auction_id) REFERENCES auction(id)
);