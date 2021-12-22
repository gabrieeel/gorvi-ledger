CREATE TABLE movement(
    id SERIAL PRIMARY KEY,
    amount_in NUMERIC(36, 18) DEFAULT 0,
    amount_out NUMERIC(36, 18) DEFAULT 0,
    is_fee BOOLEAN DEFAULT FALSE,
    balance_id INTEGER NOT NULL REFERENCES balance(id),
    operation_id INTEGER NOT NULL REFERENCES operation(id)
);
