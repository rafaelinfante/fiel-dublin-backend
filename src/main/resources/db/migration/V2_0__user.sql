USE
    fieldublin;

CREATE TABLE `users`
(
    `id`           int          NOT NULL AUTO_INCREMENT,
    `username`     varchar(64)  NOT NULL,
    `firstname`    varchar(128) NOT NULL,
    `lastname`     varchar(128) NOT NULL,
    `password`     varchar(256) NOT NULL,
    `country`      varchar(64)  NOT NULL,
    `date_created` datetime     NOT NULL,
    `date_edited`  datetime     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;