Alter  TABLE IF EXISTS "user" DROP CONSTRAINT IF EXISTS user_username_unique;
Alter  TABLE  "user" ADD CONSTRAINT user_username_unique  UNIQUE ("username");