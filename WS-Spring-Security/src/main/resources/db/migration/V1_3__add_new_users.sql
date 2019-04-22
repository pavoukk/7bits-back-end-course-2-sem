INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$NuVDpjSt4O.N9UV8E1JOhOGvijChENbD9FoVGXA/NMaBaXPHD/zqe', TRUE);
INSERT INTO users (username, password, enabled) VALUES('im_already_tracer', '$2a$10$G/Wb2VSgqmxcDDZ/bqBXJe/Z2NhgpA6HassesZVyPRN5ieKJNse2e', TRUE);

INSERT INTO authorities (username, authority) VALUES('admin', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES('im_already_tracer', 'USER');