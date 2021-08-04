CREATE TABLE account(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE currency(
    id SERIAL PRIMARY KEY,
    code VARCHAR(10)
);

CREATE TABLE balance(
    id SERIAL PRIMARY KEY,
    current_value NUMERIC(16, 8) DEFAULT 0,
    account_id INTEGER NOT NULL UNIQUE REFERENCES account(id),
    currency_id INTEGER NOT NULL UNIQUE REFERENCES currency(id)
);
