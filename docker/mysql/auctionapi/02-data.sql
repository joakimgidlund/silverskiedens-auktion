-- Lots
INSERT INTO lot (title, description, image_path, published) VALUES
('Vintage Rolex', '1960s Rolex Submariner', '/img/rolex.jpg', true),
('Antique Vase', 'Ming dynasty replica vase', '/img/vase.jpg', true),
('Classic Guitar', '1959 Gibson Les Paul', '/img/guitar.jpg', true);

-- Auctions
INSERT INTO auction (estimate, current_bid, end_time, lot_id) VALUES
(10000.00, 12500.00, NOW() + INTERVAL 2 DAY, 1),
(5000.00, 6200.00, NOW() + INTERVAL 1 DAY, 2),
(25000.00, 30000.00, NOW() + INTERVAL 5 DAY, 3);

-- Bids
INSERT INTO bid (auction_id, user_id, bid_amount, timestamp) VALUES
(1, 101, 11000.00, NOW() - INTERVAL 2 HOUR),
(1, 102, 12500.00, NOW() - INTERVAL 30 MINUTE),
(2, 103, 6000.00, NOW() - INTERVAL 1 HOUR),
(3, 104, 28000.00, NOW() - INTERVAL 3 HOUR),
(3, 105, 30000.00, NOW() - INTERVAL 1 HOUR);