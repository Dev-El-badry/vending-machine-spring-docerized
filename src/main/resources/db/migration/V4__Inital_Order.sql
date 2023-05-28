CREATE TABLE IF NOT EXISTS "order" (
                             "id" bigserial PRIMARY KEY,
                             "user_id" bigint NOT NULL,
                             "product_id" bigint NOT NULL,
                             "amount" integer NOT NULL,
                             "created_at" timestamptz NOT NULL DEFAULT 'now()'
);

CREATE INDEX ON "order" ("user_id");
CREATE INDEX ON "order" ("product_id");
CREATE INDEX ON "order" ("user_id", "product_id");

ALTER TABLE "order" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
ALTER TABLE "order" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");