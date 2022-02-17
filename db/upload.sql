CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name TEXT,
                                     email TEXT,
                                     password TEXT
);

CREATE TABLE IF NOT EXISTS task (
                                    id SERIAL PRIMARY KEY,
                                    description TEXT,
                                    created TIMESTAMP,
                                    done boolean,
                                    user_id int not null references users(id)
);