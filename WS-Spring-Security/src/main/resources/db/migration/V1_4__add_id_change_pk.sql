ALTER TABLE users DROP CONSTRAINT users_pkey CASCADE;
ALTER TABLE users ADD COLUMN id VARCHAR(36);
UPDATE users SET id = '51bc60e3-61f6-4850-997b-e78a0d83807d' WHERE username = 'admin';
UPDATE users SET id = '58acddc7-afe3-4171-b196-6223d54c46c8' WHERE username = 'im_already_tracer';
ALTER TABLE users ALTER COLUMN id SET NOT NULL;
ALTER TABLE users ADD PRIMARY KEY(id);

ALTER TABLE authorities ADD COLUMN id VARCHAR(36) REFERENCES users(id) ON DELETE CASCADE;
UPDATE authorities SET id = '51bc60e3-61f6-4850-997b-e78a0d83807d' WHERE username = 'admin';
UPDATE authorities SET id = '58acddc7-afe3-4171-b196-6223d54c46c8' WHERE username = 'im_already_tracer';
ALTER TABLE authorities ALTER COLUMN id SET NOT NULL;
ALTER TABLE authorities DROP CONSTRAINT authorities_pkey;
ALTER TABLE authorities ADD PRIMARY KEY (id, authority);
ALTER TABLE authorities DROP COLUMN username;
