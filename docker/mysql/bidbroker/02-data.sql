INSERT INTO submitted_bid (auction_id, user_id, bid_amount, timestamp) VALUES
(1, 101, 11000.00, NOW() - INTERVAL 3 HOUR),
(1, 102, 12500.00, NOW() - INTERVAL 1 HOUR),
(2, 103, 6000.00, NOW() - INTERVAL 2 HOUR),
(3, 104, 28000.00, NOW() - INTERVAL 4 HOUR),
(3, 105, 30000.00, NOW() - INTERVAL 2 HOUR);