USE
    fieldublin;

ALTER TABLE users
    ADD CONSTRAINT email_UNIQUE UNIQUE (username);

CREATE TABLE `roles`
(
    `id`   int(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `users_roles`
(
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    KEY `user_fk_idx` (`user_id`),
    KEY `role_fk_idx` (`role_id`),
    CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

INSERT INTO fieldublin.users (id, username, firstname, lastname, password, country, date_created, date_edited)
VALUES (1, 'rafael@telclic.net', 'Rafael', 'Admin', 'password', 'BR', '2024-01-26 13:27:16', '2024-01-26 13:27:21');
INSERT INTO fieldublin.users (id, username, firstname, lastname, password, country, date_created, date_edited)
VALUES (2, 'rafael+user@telclic.net', 'Rafael', 'User', 'password', 'BR', '2024-01-26 13:27:16', '2024-01-26 13:27:21');


INSERT INTO `roles` (`name`)
VALUES ('ADMIN');
INSERT INTO `roles` (`name`)
VALUES ('USER');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (1, 1);

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (2, 2);