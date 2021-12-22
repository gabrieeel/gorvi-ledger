CREATE TYPE OPERATION_TYPE AS ENUM('DEPOSIT','WITHDRAWAL','PURCHASE');

CREATE TABLE operation(
    id SERIAL PRIMARY KEY,
    created TIMESTAMP,
    type OPERATION_TYPE,
    reference_id VARCHAR(64),
    notes VARCHAR(255),
    source_operation_id INTEGER UNIQUE REFERENCES operation(id)
);
