CREATE TYPE OPERATION_TYPE AS ENUM('DEPOSIT','WITHDRAWAL','PURCHASE');

CREATE TABLE operation(
    id SERIAL PRIMARY KEY,
    created TIMESTAMP,
    type OPERATION_TYPE,
    reference_id VARCHAR(50),
    notes VARCHAR(255),
    exchange_rate NUMERIC(16, 8) DEFAULT 0,
    source_operation_id INTEGER UNIQUE REFERENCES operation(id)
);
