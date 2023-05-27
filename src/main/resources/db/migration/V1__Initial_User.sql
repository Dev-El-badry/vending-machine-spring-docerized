CREATE TABLE IF NOT EXISTS "user" (
                          id BIGSERIAL PRIMARY KEY,
                          username TEXT NOT NULL,
                          password TEXT NOT NULL
);