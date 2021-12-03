INSERT INTO USERS (name, password)
VALUES ('User', '{noop}password'),
       ('Admin', '{noop}admin'),
       ('Operator', '{noop}1234');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('OPERATOR', 3);

