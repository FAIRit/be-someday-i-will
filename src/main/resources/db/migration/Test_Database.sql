DROP TABLE IF EXISTS app_users;
DROP TABLE IF EXISTS avatars;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS books;
CREATE TABLE app_users
(
    id                   bigint       NOT NULL AUTO_INCREMENT,
    created_at           date,
    email                varchar(255) NOT NULL,
    name                 varchar(255) NOT NULL,
    newsletter_frequency varchar(255),
    password             varchar(255),
    updated_at           date,
    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

CREATE TABLE avatars
(
    id        bigint NOT NULL AUTO_INCREMENT,
    data      longblob,
    file_type varchar(255),
    user_id   bigint,
    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

CREATE TABLE books
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    authors     varchar(255),
    buy_link    varchar(255),
    categories  varchar(255),
    description varchar(1000),
    image_link  varchar(255),
    page_count  varchar(255),
    subtitle    varchar(255),
    title       varchar(255) NOT NULL,
    user_id     bigint,
    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

CREATE TABLE movies
(
    id           bigint       NOT NULL AUTO_INCREMENT,
    description  varchar(1000),
    genres       varchar(255),
    poster_link  varchar(255),
    release_date date,
    runtime      integer,
    title        varchar(255) NOT NULL,
    user_id      bigint,
    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

ALTER TABLE avatars
    ADD FOREIGN KEY (user_id) REFERENCES app_users (id);
ALTER TABLE books
    ADD FOREIGN KEY (user_id) REFERENCES app_users (id);
ALTER TABLE movies
    ADD FOREIGN KEY (user_id) REFERENCES app_users (id);

INSERT INTO app_users
VALUES (1, NULL, 'test-user1@test.com', 'John', 'WEEKLY', 'ssjkajskxkls', NULL),
       (2, NULL, 'test-user2@test.com', 'Tom', 'NEVER', 'ssxknmlkaskja!TP', NULL),
       (3, NULL, 'test-user3@test.com', 'Jane', 'WEEKLY', 'ssjkajxpoasp9', NULL),
       (4, NULL, 'test-user4@test.com', 'Kate', 'MONTHLY', 'sXJxj8q9weHDjaP', NULL);


