DROP SCHEMA public CASCADE;
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS users
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    balance NUMERIC(10,2)
    );

CREATE TABLE IF NOT EXISTS transactions
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(30),
    amount NUMERIC(10,2) NOT NULL,
    date DATE NOT NULL
    );

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS transaction_category
(
    transaction_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    PRIMARY KEY (transaction_id,category_id)
    );