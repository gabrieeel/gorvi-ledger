CREATE TABLE ledger_user(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE company(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE account(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES ledger_user(id),
    company_id INTEGER NOT NULL REFERENCES company(id)
);

CREATE TABLE currency(
    id SERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100)
);

CREATE TABLE balance(
    id SERIAL PRIMARY KEY,
    current_value NUMERIC(36, 18) DEFAULT 0,
    account_id INTEGER NOT NULL REFERENCES account(id),
    currency_id INTEGER NOT NULL REFERENCES currency(id)
);
