-- init.sql
CREATE TABLE IF NOT EXISTS products (
    "id" BIGINT,
    "name" VARCHAR(255),
    "description" VARCHAR(255),
    "url" VARCHAR(255),
    "type" INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO products VALUES (1, 'Guitar Fender Stratocaster', 'A classic electric guitar with legendary sound.', 'images/guitar_fender.png', 1);
INSERT INTO products VALUES (2, 'Piano Yamaha P-45', 'Digital piano with excellent sound quality and portability.', 'images/piano_yamaha.png', 1);
INSERT INTO products VALUES (3, 'Drum Set Pearl Export', 'Complete drum set perfect for beginners and professionals.', 'images/drum_set_pearl.png', 1);
INSERT INTO products VALUES (4, 'Violin Stentor Student I', 'An excellent violin for beginners with a warm tone.', 'images/violin_stentor.png', 1);
INSERT INTO products VALUES (5, 'Sennheiser HD 280 Pro Headphones', 'High-quality headphones ideal for studio monitoring.', 'images/headphones_sennheiser.png', 1);
INSERT INTO products VALUES (6, 'Nike Air Zoom Pegasus 40', 'Versatile running shoes designed for comfort and performance.', 'images/nike_air_zoom.png', 2);
INSERT INTO products VALUES (7, 'Adidas Predator Soccer Ball', 'Top-quality soccer ball for professional use.', 'images/adidas_predator_ball.png', 2);
INSERT INTO products VALUES (8, 'Wilson Pro Staff Tennis Racket', 'Lightweight and powerful racket for all skill levels.', 'images/tennis_racket_wilson.png', 2);
INSERT INTO products VALUES (9, 'Spalding NBA Basketball', 'Official basketball of the NBA.', 'images/spalding_nba_ball.png', 2);
INSERT INTO products VALUES (10, 'Fitbit Charge 5 Fitness Tracker', 'Advanced fitness tracker with heart rate monitoring.', 'images/fitbit_charge.png', 2);
INSERT INTO products VALUES (11, 'Apple iPhone 15', 'Latest iPhone with cutting-edge features and sleek design.', 'images/iphone_15.png', 3);
INSERT INTO products VALUES (12, 'Samsung Galaxy Tab S9', 'Powerful tablet for work and entertainment.', 'images/galaxy_tab_s9.png', 3);
INSERT INTO products VALUES (13, 'Sony PlayStation 5', 'Next-gen gaming console with incredible graphics.', 'images/ps5.png', 3);
INSERT INTO products VALUES (14, 'Dell XPS 13 Laptop', 'Ultra-thin laptop with powerful performance.', 'images/dell_xps_13.png', 3);
INSERT INTO products VALUES (15, 'GoPro HERO 12', 'Compact action camera with stunning video quality.', 'images/gopro_hero12.png', 3);
INSERT INTO products VALUES (16, 'Levi\’s 501 Jeans', 'Classic straight fit jeans with timeless style.', 'images/levis_501.png', 4);
INSERT INTO products VALUES (17, 'Nike Sportswear Hoodie', 'Comfortable hoodie with a sporty design.', 'images/nike_hoodie.png', 4);
INSERT INTO products VALUES (18, 'Adidas Ultraboost Shoes', 'Stylish running shoes with superior cushioning.', 'images/adidas_ultraboost.png', 4);
INSERT INTO products VALUES (19, 'Zara Cotton Shirt', 'Casual cotton shirt with a modern fit.', 'images/zara_shirt.png', 4);
INSERT INTO products VALUES (20, 'H&M Denim Jacket', 'Classic denim jacket perfect for layering.', 'images/hm_denim_jacket.png', 4);
INSERT INTO products VALUES (21, 'Yamaha Acoustic Guitar', 'Versatile acoustic guitar suitable for all levels.', 'images/yamaha_guitar.png', 1);
INSERT INTO products VALUES (22, 'Roland Electronic Drum Kit', 'State-of-the-art drum kit for professionals.', 'images/roland_drum_kit.png', 1);
INSERT INTO products VALUES (23, 'Wilson Volleyball', 'Durable volleyball for beach and court play.', 'images/wilson_volleyball.png', 2);
INSERT INTO products VALUES (24, 'Apple MacBook Air', 'Lightweight and powerful laptop for everyday use.', 'images/macbook_air.png', 3);
INSERT INTO products VALUES (25, 'Samsung Galaxy Watch', 'Smartwatch with fitness tracking and notifications.', 'images/galaxy_watch.png', 3);
INSERT INTO products VALUES (26, 'Under Armour Joggers', 'Comfortable and stylish joggers for workouts.', 'images/under_armour_joggers.png', 4);
INSERT INTO products VALUES (27, 'Ray-Ban Aviator Sunglasses', 'Timeless sunglasses with UV protection.', 'images/rayban_aviator.png', 4);
INSERT INTO products VALUES (28, 'Gucci Leather Belt', 'Premium leather belt with iconic design.', 'images/gucci_belt.png', 4);
INSERT INTO products VALUES (29, 'Casio Digital Keyboard', 'Portable keyboard with various instrument sounds.', 'images/casio_keyboard.png', 1);
INSERT INTO products VALUES (30, 'Puma Running Shoes', 'Lightweight running shoes for everyday training.', 'images/puma_running_shoes.png', 2);
INSERT INTO products VALUES (31, 'Beats Studio Buds', 'Noise-cancelling earbuds with immersive sound.', 'images/beats_buds.png', 3);
INSERT INTO products VALUES (32, 'Lacoste Polo Shirt', 'Classic polo shirt with a modern fit.', 'images/lacoste_polo.png', 4);

CREATE TABLE IF NOT EXISTS recommendations (
    "id" VARCHAR(255),
    "userId" VARCHAR(255),
    "productId" VARCHAR(255),
    "score" DOUBLE PRECISION,
    "timestamp" BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS purchases (
    "id" SERIAL,
    "userId" VARCHAR(255),
    "timestamp" BIGINT DEFAULT (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS purchases_details (
    "id" SERIAL,
    "purchaseId" BIGINT,
    "productId" VARCHAR(255),
    "amount" BIGINT,
    PRIMARY KEY (id)
);