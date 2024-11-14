-- init.sql
CREATE TABLE IF NOT EXISTS recommendations (
    "id" VARCHAR(255),
    "userId" VARCHAR(255),
    "productId" VARCHAR(255),
    "rate" VARCHAR(50),
    "generatedAt" VARCHAR(50),
    "score" DOUBLE PRECISION,
    "timestamp" BIGINT,
    PRIMARY KEY (id)
);