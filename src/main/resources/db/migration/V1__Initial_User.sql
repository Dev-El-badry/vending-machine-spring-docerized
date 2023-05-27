CREATE TABLE IF NOT EXISTS "user" (
                          id BIGSERIAL PRIMARY KEY,
                          username TEXT NOT NULL,
                          password TEXT NOT NULL,
                          deposit INTEGER NOT NULL DEFAULT 0,
                          role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);