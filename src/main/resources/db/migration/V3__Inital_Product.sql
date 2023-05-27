CREATE TABLE "product" (
                            "id" BIGSERIAL PRIMARY KEY,
                            "name" varchar NOT NULL,
                            "price" INTEGER NOT NULL DEFAULT 0,
                            "qty" INTEGER NOT NULL DEFAULT 0,
                            "user_id" BIGSERIAL NOT NULL
);

ALTER TABLE "product" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
