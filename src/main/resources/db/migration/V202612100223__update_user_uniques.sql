ALTER TABLE users RENAME INDEX username TO UK_users__username;
ALTER TABLE users RENAME INDEX email_unique TO UK_users__email;