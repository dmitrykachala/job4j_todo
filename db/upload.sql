CREATE TABLE IF NOT EXISTS item (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done boolean
);