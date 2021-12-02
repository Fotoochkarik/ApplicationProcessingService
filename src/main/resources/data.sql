INSERT INTO USERS (name, password)
-- VALUES ('User', 'password'),
--        ('Admin', 'admin');
VALUES ('User', '{noop}password'),
       ('Admin', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);
