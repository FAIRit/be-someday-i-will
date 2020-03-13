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
    description varchar(8000),
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
    description  varchar(8000),
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
